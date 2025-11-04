package com.kaust.ms.manager.prompt.auth.domain.ports;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface AuthPort {

    /**
     * Verify firebase token.
     *
     * @param token {@link String}
     * @return {@link String}
     */
    Mono<UserData> verifyToken(String token);

    /**
     * Get user data.
     *
     * @param userUid {@link String}
     * @return {@link UserRecord}
     */
    Mono<UserData> getUserData(String userUid);

}
