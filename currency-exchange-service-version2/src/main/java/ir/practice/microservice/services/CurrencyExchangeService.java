package ir.practice.microservice.services;

import ir.practice.microservice.domain.CurrencyExchange;

public interface CurrencyExchangeService {

    CurrencyExchange findByFromAndTo(String from, String to);
}
