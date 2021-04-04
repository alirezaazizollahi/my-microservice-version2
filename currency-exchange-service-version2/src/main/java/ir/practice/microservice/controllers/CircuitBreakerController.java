package ir.practice.microservice.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
    public String sampleApi(){

        logger.info("Sample Api call received");
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity("http://localhost", String.class);
        return responseEntity.getBody();
    }

    @GetMapping("/circuit-breaker-api")
    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    public String circuitBreakerApi(){

        logger.info("circuit breaker Api call received");
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity("http://localhost", String.class);
        return responseEntity.getBody();
    }

    public String hardCodedResponse(Exception e){
//        logger.info("hardCodedResponse Api call received");
        return "fallback-response";
    }
}
