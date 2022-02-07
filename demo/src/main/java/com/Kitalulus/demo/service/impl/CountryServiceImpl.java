package com.Kitalulus.demo.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Kitalulus.demo.controller.exceptions.DomainException;
import com.Kitalulus.demo.dto.responce.CountryDetailRes;
import com.Kitalulus.demo.entity.AuditDetails;
import com.Kitalulus.demo.rest.client.RestClient;
import com.Kitalulus.demo.service.AuditService;
import com.Kitalulus.demo.service.CountryService;
import com.Kitalulus.demo.utill.JWTUtill;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CountryServiceImpl implements CountryService {
    Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final RestClient restClient;
    private final AuditService audit;

    @Value("${country.details.url}")
    String countryDetails;
    @Value("${currency.exchange.details.url}")
    String currenciesDetails;

    @Autowired
    public CountryServiceImpl(RestClient restClient, AuditService audit) {
        this.restClient = restClient;
        this.audit = audit;
    }

    @Override
    public CountryDetailRes getCountryDetails(String countryName) {
        CountryDetailRes response = new CountryDetailRes();

        final String path = countryDetails + countryName;
        String countryInfo = restClient.getCall(path);
        String countryfullName;
        if (!countryInfo.isEmpty()) {
            // for demo only parsing it into json not to demo
            JsonNode json = parseApiResponse(countryInfo);
            countryfullName = json.get(0).get("name").get("official").asText();
            response.setFullname(countryfullName);
            response.setPopulation(json.get(0).get("population").toString());

            Map<String, Object> currency = parseCurrency(json);
            if (currency != null) {
                response.setCurrency(currency);
                String key = currency.entrySet().iterator().next().getKey();
                // currently exchange rate is for EUR because in free account we can not change
                // base country
                String exchange = restClient.getCall(currenciesDetails + key);

                if (!exchange.isEmpty()) {
                    JsonNode exchangejson = parseApiResponse(exchange);
                    String exchangeRate = exchangejson.get("rates").get(key).toString();
                    response.setExchangeRate(exchangeRate);
                    // storing audits
                    AuditDetails details = new AuditDetails();
                    details.setCountryName(countryfullName);
                    details.setCurrency(key);
                    details.setExchangeRate(exchangeRate);
                    details.setUser(JWTUtill.getCurrentUser());
                    audit.save(details);
                }
            }
        } else {
            logger.warn("No details found against " + countryName);
            throw new DomainException(HttpStatus.NOT_FOUND, "404", "country details api not found country details");
        }
        return response;
    }

    private JsonNode parseApiResponse(String res) {
        try {
            ObjectMapper jacksonObjMapper = new ObjectMapper();
            JsonNode json = jacksonObjMapper.readTree(res);
            return json;
        } catch (Exception e) {
            throw new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Api response not parsed");

        }
    }

    private Map<String, Object> parseCurrency(JsonNode json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> result = mapper.convertValue(json.get(0).get("currencies"),
                    new TypeReference<Map<String, Object>>() {
                    });
            return result;
        } catch (Exception e) {
            logger.warn("Currency Details Not found");
            return null;
        }
    }
}
