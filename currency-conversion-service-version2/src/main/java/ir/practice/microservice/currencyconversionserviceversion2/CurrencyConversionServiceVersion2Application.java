package ir.practice.microservice.currencyconversionserviceversion2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:service.properties")
@EnableFeignClients
public class CurrencyConversionServiceVersion2Application {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceVersion2Application.class, args);
	}

}
