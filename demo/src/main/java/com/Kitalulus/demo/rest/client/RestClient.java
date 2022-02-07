package com.Kitalulus.demo.rest.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Kitalulus.demo.controller.exceptions.DomainException;

@Service
public class RestClient {
    Logger logger = LoggerFactory.getLogger(RestClient.class);
    private final RestTemplate restTamplate;

    @Autowired
    public RestClient(RestTemplate restTamplate) {
        this.restTamplate = restTamplate;
    }

    public String getCall(String path) {
        try {
            logger.info("Api call with url of " + path);
            URI uri = new URI(path);
            ResponseEntity<String> result = restTamplate.getForEntity(uri, String.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                return result.getBody().toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            logger.error("Exception in Api call: ", e);
            throw new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Something went wrong in Api call");
        }
    }
}
