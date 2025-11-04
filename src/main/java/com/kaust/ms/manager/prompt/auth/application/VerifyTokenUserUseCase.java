package com.kaust.ms.manager.prompt.auth.application;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.auth.domain.ports.AuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VerifyTokenUserUseCase implements IVerifyTokenUserUseCase {

    /** authPort. */
    private final AuthPort authPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<UserData> handle(final String token) {
        return authPort.verifyToken(token);
    }

}
