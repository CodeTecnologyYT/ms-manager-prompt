package com.kaust.ms.manager.prompt.shared.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Order(-2)
public class GlobalExceptionConfig implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        final var response = new HashMap<String, Object>();
        response.put("path", exchange.getRequest().getPath().value());
        if (ex instanceof ManagerPromptException bindingsException) {
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(bindingsException.getStatus()));
            response.put("timestamp", LocalDateTime.now());
            response.put("status", bindingsException.getStatus());
            response.put("error", bindingsException.getErrorEnum());
            response.put("message", bindingsException.getErrorEnum().getMessage());
            response.put("code", bindingsException.getErrorEnum().getCode());
            try {
                final var responseBytes = objectMapper.writeValueAsBytes(response);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                final var buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);
                return exchange.getResponse().writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                return Mono.error(ex);
            }
        }

        // Manejo de errores de validación
        if (ex instanceof WebExchangeBindException bindException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            final var errors = bindException.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("error", "Error de Validación");
            response.put("message", "Error en la validación de los datos enviados");
            response.put("errors", errors);

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

