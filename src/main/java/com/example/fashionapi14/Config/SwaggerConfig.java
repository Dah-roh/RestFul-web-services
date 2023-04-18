package com.example.fashionapi14.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${application.version}")
    private String version;

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .title("Fashion Blog API")
                        .description("Api that provides crud operations for a fashion blog.")
                        .version(version));
    }


    @Bean
    public GroupedOpenApi usersEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Users")
                .pathsToMatch("/users").build();
    }

    @Bean
    public GroupedOpenApi adminEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Admin")
                .pathsToMatch("/admin").build();
    }

    @Bean
    public GroupedOpenApi postEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Post")
                .pathsToMatch("/post").build();
    }

}
