package ir.practice.microservice.currencyconversionserviceversion2.services;

import ir.practice.microservice.currencyconversionserviceversion2.domain.CurrencyConversion;

import java.math.BigDecimal;

public interface CurrencyConversionService {

    CurrencyConversion calculateCurrencyConversion(String from, String to, BigDecimal quantity);
    CurrencyConversion calculateCurrencyConversionWithObjectMapper(String from, String to, BigDecimal quantity);
    CurrencyConversion calculateCurrencyConversionWithFeign(String from, String to, BigDecimal quantity);
}
