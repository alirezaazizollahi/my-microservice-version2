package ir.practice.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamingServerVersion2Application {

	public static void main(String[] args) {
		SpringApplication.run(NamingServerVersion2Application.class, args);
	}

}
