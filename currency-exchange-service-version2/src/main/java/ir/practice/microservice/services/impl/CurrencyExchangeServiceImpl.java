package ir.practice.microservice.services.impl;

import ir.practice.microservice.domain.CurrencyExchange;
import ir.practice.microservice.repositories.CurrencyExchangeRepository;
import ir.practice.microservice.services.CurrencyExchangeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private CurrencyExchangeRepository currencyExchangeRepository;

    public CurrencyExchange findByFromAndTo(String from, String to){
        return currencyExchangeRepository.findByFromAndTo(from, to);
    }


}
