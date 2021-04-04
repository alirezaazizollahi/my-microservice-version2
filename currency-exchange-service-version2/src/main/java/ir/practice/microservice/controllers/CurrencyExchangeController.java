package ir.practice.microservice.controllers;

import ir.practice.microservice.domain.CurrencyExchange;
import ir.practice.microservice.services.CurrencyExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CurrencyExchangeController {

    private Environment environment;
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
        CurrencyExchange currencyExchange = currencyExchangeService.findByFromAndTo(from, to);
        if (currencyExchange == null){
            throw new RuntimeException("Unable to find data from " + from + " to " + to);
        }
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
