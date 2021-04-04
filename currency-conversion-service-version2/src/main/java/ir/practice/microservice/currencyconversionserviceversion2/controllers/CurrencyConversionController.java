package ir.practice.microservice.currencyconversionserviceversion2.controllers;

import ir.practice.microservice.currencyconversionserviceversion2.domain.CurrencyConversion;
import ir.practice.microservice.currencyconversionserviceversion2.services.CurrencyConversionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CurrencyConversionController {

    private CurrencyConversionService currencyConversionService;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable BigDecimal quantity){

        CurrencyConversion currencyConversion = currencyConversionService.calculateCurrencyConversionWithFeign(from, to, quantity);
        return currencyConversion;
    }

}
