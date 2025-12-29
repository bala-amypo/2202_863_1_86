package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    @Primary // Spring will use this bean for injection
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://9093.32procr.amypo.ai/")
                ))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("BearerAuth",
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

    // Bean used by the test only
    @Bean
    public OpenAPI api() {
        return customOpenAPI();
    }
}

// package com.example.demo.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.servers.Server;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//                 .servers(List.of(
//                         new Server().url("https://9384.pro604cr.amypo.ai/")
//                 ))
//                 // 🔑 Enable Authorize button
//                 .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
//                 .components(
//                         new io.swagger.v3.oas.models.Components()
//                                 .addSecuritySchemes("BearerAuth",
//                                         new SecurityScheme()
//                                                 .name("Authorization")
//                                                 .type(SecurityScheme.Type.HTTP)
//                                                 .scheme("bearer")
//                                                 .bearerFormat("JWT")
//                                 )
//                 );
//     }
// }

// package com.example.demo.config;

// import io.swagger.v3.oas.models.*;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.security.*;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;
// @Configuration
// public class SwaggerConfig {
//     @Bean
//     public OpenAPI api() {
//         return new OpenAPI()
//                 .info(new Info().title("Crop & Fertilizer Suggestion API").version("1.0"))
//                 .components(new Components()
//                         .addSecuritySchemes("bearer-key",
//                                 new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//                 .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
//     }
// }