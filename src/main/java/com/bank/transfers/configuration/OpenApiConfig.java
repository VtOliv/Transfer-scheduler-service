package com.bank.transfers.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI transferSchedulerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transfer Scheduler API")
                        .description("API para agendamento de transferências financeiras com cálculo de taxa")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Projeto GitHub")
                        .url("https://github.com/VtOliv/Transfer-scheduler-service"));
    }
}