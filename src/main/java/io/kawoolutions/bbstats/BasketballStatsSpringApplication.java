package io.kawoolutions.bbstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Basketball Stats Spring Boot REST API",
                description = "Demo application for Spring Boot features",
                version = "1.0.0",
                contact = @Contact(email = "info@kawoolutions.io")
        ),
        servers = @Server(
                url = "http://{host}:{port}{context}",
                variables = {
                        @ServerVariable(name = "host", defaultValue = "localhost"),
                        @ServerVariable(name = "port", defaultValue = "8081"),
                        @ServerVariable(name = "context", defaultValue = "/rest")
                })
)
public class BasketballStatsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasketballStatsSpringApplication.class, args);
    }

}
