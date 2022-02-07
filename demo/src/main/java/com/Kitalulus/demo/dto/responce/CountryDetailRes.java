package com.Kitalulus.demo.dto.responce;

import java.util.Map;

import lombok.Data;

@Data
public class CountryDetailRes {
    private String fullname;
    private String population;
    private String exchangeRate;
    private Map<String, Object> Currency;
}
