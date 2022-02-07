package com.Kitalulus.demo.service;

import com.Kitalulus.demo.dto.responce.CountryDetailRes;

public interface CountryService {

    CountryDetailRes getCountryDetails(String countryName);

}
