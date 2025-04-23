package com.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication(scanBasePackages = {
		"com.api.client",
		"com.api.persistence",
		"com.api.core"
})
@EntityScan("com.api.persistence.domain")
@EnableAsync
@Slf4j
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories("com.api.client.repository")
public class ApiApplication implements WebMvcConfigurer {
	
	@Value("${openApi.info.title}")
	private String openApiTitle;
	@Value("${openApi.info.description}")
	private String openApiDescription;
	@Value("${openApi.info.version}")
	private String openApiVersion;
	
	@Value("${cors.mapping: }")
	private String corsMapping; 

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	@Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(openApiTitle)
                .description(openApiDescription)
                .version(openApiVersion))
                .addServersItem(new Server().url("/"));
    }

}
