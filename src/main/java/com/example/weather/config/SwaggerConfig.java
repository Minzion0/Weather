//package com.example.weather.config;
//
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public GroupedOpenApi SignApi() {
//        return GroupedOpenApi.builder()
//                .group("sign")
//                .pathsToMatch("/api/sign-up","/api/sign-in","/api/refresh-token")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi MemberApi() {
//        return GroupedOpenApi.builder()
//                .group("member")
//                .pathsToMatch("/api/members/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi CategoryApi() {
//        return GroupedOpenApi.builder()
//                .group("category")
//                .pathsToMatch("/api/categories/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi PostApi() {
//        return GroupedOpenApi.builder()
//                .group("post")
//                .pathsToMatch("/api/posts/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi CommentApi() {
//        return GroupedOpenApi.builder()
//                .group("comment")
//                .pathsToMatch("/api/comments/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi MessageApi() {
//        return GroupedOpenApi.builder()
//                .group("message")
//                .pathsToMatch("/api/messages/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("O2O Automatic Store System Demo")
//                        .version("1.0")
//                        .description("O2O Automatic Store System Demo REST API Documentation"))
//                .components(new io.swagger.v3.oas.models.Components());
//    }
//}