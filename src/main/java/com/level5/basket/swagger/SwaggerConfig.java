package com.level5.basket.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import java.util.Collections;

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
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))

                .schema("UserLoginRequestDto", new Schema<>()
                        .type("object")
                        .addProperty("email", new StringSchema().example("user@example.com"))
                        .addProperty("password", new StringSchema().example("password123!"))
                )

                // 로그인 수동경로 지정
                .path("/api/users/login", new PathItem()
                        .post(new Operation()
                                .summary("로그인")
                                .description("사용자 로그인을 위한 엔드포인트")
                                .tags(Collections.singletonList("auth"))
                                .operationId("loginUser")
                                .requestBody(new RequestBody()
                                        .content(new Content()
                                                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserLoginRequestDto"))))
                                )
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("로그인 성공"))
                                        .addApiResponse("401", new ApiResponse().description("인증 실패"))
                                )));
    }

    // 로그인 컨트롤러
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("custom-auth")
                .pathsToMatch("/api/users/login")
                .build();
    }

    // 회원가입 컨트롤러
    @Bean
    public GroupedOpenApi registrationApi() {
        return GroupedOpenApi.builder()
                .group("custom-regis")
                .pathsToMatch("/api/users/join") // 회원가입 API 경로 지정
                .build();
    }

    // 장바구니 관련 컨트롤러
    @Bean
    public GroupedOpenApi cartApi() {
        return GroupedOpenApi.builder()
                .group("carts")
                .pathsToMatch("/api/carts/**")
                .build();
    }

    // 상품 관련 컨트롤러
    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/api/product/**")
                .build();
    }

}