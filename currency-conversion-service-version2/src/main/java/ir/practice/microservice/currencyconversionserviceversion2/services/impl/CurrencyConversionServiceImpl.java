package ir.practice.microservice.currencyconversionserviceversion2.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.practice.microservice.currencyconversionserviceversion2.domain.CurrencyConversion;
import ir.practice.microservice.currencyconversionserviceversion2.feignproxy.CurrencyExchangeProxy;
import ir.practice.microservice.currencyconversionserviceversion2.services.CurrencyConversionService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class CurrencyConversionServiceImpl implements CurrencyConversionService {


    @Value("${currency.exchange.service-base-url}")
    private String currencyExchangeEerviceBaseUrl;

    @Value("${currency.exchange-url}")
    private String currencyExchangeUrl;

    private ObjectMapper objectMapper;

    private CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyConversionServiceImpl(ObjectMapper objectMapper, CurrencyExchangeProxy currencyExchangeProxy) {
        this.objectMapper = objectMapper;
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @Override
    public CurrencyConversion calculateCurrencyConversion(String from, String to, BigDecimal quantity) {

        Map<String, String> variables = new HashMap<>();
        variables.put("from", from);
        variables.put("to", to);

        String url = currencyExchangeEerviceBaseUrl + currencyExchangeUrl;

        ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(url, CurrencyConversion.class, variables);
        CurrencyConversion currencyConversion = response.getBody();

        return new CurrencyConversion(currencyConversion.getId(), from, to,  quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity), currencyConversion.getEnvironment());

    /*    return new CurrencyConversion(1001L, from, to,  quantity,
                                                           BigDecimal.valueOf(65),
                                                           BigDecimal.valueOf(1300), "8001");*/
    }

    @Override
    public CurrencyConversion calculateCurrencyConversionWithObjectMapper(String from, String to, BigDecimal quantity) {

        String restUrl = currencyExchangeEerviceBaseUrl + currencyExchangeUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Map<String, String> variables = new HashMap<>();
        variables.put("from", from);
        variables.put("to", to);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(restUrl, HttpMethod.GET, request, String.class, variables);
        } catch (
                HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                throw new RuntimeException(e.getMessage());
            }
        }
        CurrencyConversion currencyConversion = null;
        try {
            currencyConversion = objectMapper.readValue(response.getBody(), new TypeReference<CurrencyConversion>() {
            });

        } catch (IOException e) {
            throw new RuntimeException("Data not found");
        }
        return new CurrencyConversion(currencyConversion.getId(), from, to,  quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity), currencyConversion.getEnvironment());
    }

    public CurrencyConversion calculateCurrencyConversionWithFeign(String from, String to, BigDecimal quantity){
        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveCurrencyExchange(from, to);

        return new CurrencyConversion(currencyConversion.getId(), from, to,  quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity), currencyConversion.getEnvironment());
    }



}
