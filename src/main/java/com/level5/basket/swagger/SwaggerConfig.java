package com.level5.basket.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "JWTAuth";

    @Bean
    @Profile("!Prod") // 운영 환경에서 Swagger 비활성화
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.APIKEY) // Bearer가 아닌 APIKEY를 사용
                                .in(SecurityScheme.In.HEADER) // 헤더에 토큰을 위치
                                .name("Authorization"))) // Authorization 헤더를 사용합니다.
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}