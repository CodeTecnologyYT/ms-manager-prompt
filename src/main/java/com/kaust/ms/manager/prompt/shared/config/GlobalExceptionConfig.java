package com.kaust.ms.manager.prompt.shared.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@Order(-2)
public class GlobalExceptionConfig implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ManagerPromptException) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            final var response = new HashMap<String, Object>();
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            response.put("error", "Token Expirado");
            response.put("message", ex.getMessage());
            response.put("code", "TOKEN_EXPIRED");
            response.put("path", exchange.getRequest().getPath().value());

            try {
                final var responseBytes = objectMapper.writeValueAsBytes(response);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                final var buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);
                return exchange.getResponse().writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                return Mono.error(ex);
            }
        }
        return Mono.error(ex);
    }

}

