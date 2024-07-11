package com.lifepill.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    // List of open API endpoints (bypass these endpoints)
    public static final List<String> openApiEndpoints = List.of(
            "/authentication-service/authenticate",
            "/authentication-service/register",
            "/authentication-service/validate",
            "/authentication-service/refresh",
            "/eureka/**",
            "/api-docs/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
