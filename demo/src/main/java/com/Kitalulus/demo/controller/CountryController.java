package com.Kitalulus.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Kitalulus.demo.dto.responce.BaseResponseDto;
import com.Kitalulus.demo.dto.responce.CountryDetailRes;
import com.Kitalulus.demo.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {
    Logger logger = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(produces = "application/json", params = "countryName")
    public BaseResponseDto<CountryDetailRes> delete(@RequestParam(value = "countryName") String countryName) {
        logger.info("Country detail api called");
        return BaseResponseDto.success(countryService.getCountryDetails(countryName));

    }
}