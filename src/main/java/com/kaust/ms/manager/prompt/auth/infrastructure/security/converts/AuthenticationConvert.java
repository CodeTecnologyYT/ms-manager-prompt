package com.kaust.ms.manager.prompt.auth.infrastructure.security.converts;

import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationConvert implements ServerAuthenticationConverter {

    /** BEARER. */
    private static final String BEARER = "Bearer ";

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeader -> authHeader.substring(BEARER.length()))
                .map(token -> new UsernamePasswordAuthenticationToken(token, token));
    }

}
