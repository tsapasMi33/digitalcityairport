package be.tsapasmi33.digitalcityairport.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DigitalCityAirport API")
                        .description("An API that manages flights and reservations")
                        .version("v0.5")
                        .summary("This is an API made in DigitalCity in order to exercise with spring boot elements!"));
    }
}
