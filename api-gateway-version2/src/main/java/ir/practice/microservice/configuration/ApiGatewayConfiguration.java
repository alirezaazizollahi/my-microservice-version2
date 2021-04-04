package ir.practice.microservice.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){

        Function<PredicateSpec, Buildable<Route>> firstRouteFunction
                = p -> p.path("/get")
                .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                               .addRequestParameter("Param", "MyValue1"))
                .uri("http://httpbin.org:80");
        Function<PredicateSpec, Buildable<Route>> secondRouteFunction
                = p -> p.path("/currency-exchange/**")
                .uri("lb://currency-exchange-service");
        Function<PredicateSpec, Buildable<Route>> thirdRouteFunction
                = p -> p.path("/currency-conversion/**")
                .uri("lb://currency-conversion-service");
        Function<PredicateSpec, Buildable<Route>> forthRouteFunction
                = p -> p.path("/currency-conversion-new/**")
                .filters(f -> f.rewritePath("/currency-conversion-new", "/currency-conversion"))
                .uri("lb://currency-conversion-service");
        return builder.routes()
                .route(firstRouteFunction)
                .route(secondRouteFunction)
                .route(thirdRouteFunction)
                .route(forthRouteFunction)
                .build();
    }

}
