package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
@Bean
public OpenAPI api() {
    return new OpenAPI()
            .info(new Info()
                    .title("Demo API")
                    .version("1.0.0")
                    .description("This is a sample API documentation for the Demo project")
                    .contact(new Contact()
                            .name("Support Team")
                            .email("support@example.com")
                            .url("https://example.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://springdoc.org")))
            .servers(List.of(
                    new Server().url("http://localhost:8080").description("Local Development Server"),
                    new Server().url("https://9093.32procr.amypo.ai/").description("Production Server")
            ));
}
