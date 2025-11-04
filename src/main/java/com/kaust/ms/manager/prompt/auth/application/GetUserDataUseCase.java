package com.kaust.ms.manager.prompt.auth.application;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.auth.domain.ports.AuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetUserDataUseCase implements IGetUserDataUseCase {

    /** firebaseAuthAdapter. */
    private final AuthPort authPort;

    /**
     * Get user data.
     *
     * @param userUid {@link String}
     * @return {@link UserData}
     */
    public Mono<UserData> handle(final String userUid) {
        return authPort.getUserData(userUid);
    }

}
