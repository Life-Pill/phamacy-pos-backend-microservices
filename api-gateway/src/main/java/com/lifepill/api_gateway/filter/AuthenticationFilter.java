package com.lifepill.api_gateway.filter;

import com.lifepill.api_gateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

/*    @Autowired
    private RestTemplate restTemplate;*/

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return exchange.getResponse().setComplete();
                    throw new RuntimeException("Authorization header is missing");
                }

                String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeaders != null && authHeaders.startsWith("Bearer ")) {
                    authHeaders = authHeaders.substring(7);
                }
                try {
                    //REST call to authentication service
//                    restTemplate.getForObject("http://authentication-service/validate?token"+authHeaders, String.class);
                    jwtUtil.validateToken(authHeaders);

                } catch (Exception e) {
//                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return exchange.getResponse().setComplete();
                    System.out.println("Token is invalid");
                    throw new RuntimeException("Token is invalid");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
//11:12