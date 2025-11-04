package com.kaust.ms.manager.prompt.shared.anotations.current_user;


import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * @inheritDoc.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(UserData.class);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .filter(authentication -> authentication instanceof UsernamePasswordAuthenticationToken)
                .map(authentication -> (UsernamePasswordAuthenticationToken) authentication)
                .map(token -> (UserData) token.getPrincipal())
                .cast(Object.class);
    }
}
