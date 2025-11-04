package com.kaust.ms.manager.prompt.auth.infrastructure.security.manager;

import com.kaust.ms.manager.prompt.auth.application.IVerifyTokenUserUseCase;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    /**
     * iVerifyTokenUserUseCase.
     */
    private final IVerifyTokenUserUseCase iVerifyTokenUserUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication == null || authentication.getCredentials() == null) {
            return Mono.error(new ManagerPromptException("Token de autenticación no proporcionado"));
        }

        return iVerifyTokenUserUseCase.handle(authentication.getCredentials().toString())
                .map(userData -> {
                    final Authentication auth = new UsernamePasswordAuthenticationToken(
                            userData,
                            null,
                            userData.getRoles().stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList());
                    return auth;
                })
                .onErrorResume(ManagerPromptException.class, ex ->
                        Mono.error(new ManagerPromptException("Error en la verificacion", ex)));
    }

}
