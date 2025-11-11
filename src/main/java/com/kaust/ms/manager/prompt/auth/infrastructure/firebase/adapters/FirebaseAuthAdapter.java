package com.kaust.ms.manager.prompt.auth.infrastructure.firebase.adapters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.auth.domain.ports.AuthPort;
import com.kaust.ms.manager.prompt.auth.infrastructure.mappers.FirebaseTokenToUserDataMapper;
import com.kaust.ms.manager.prompt.auth.infrastructure.mappers.UserRecordToUserDataMapper;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class FirebaseAuthAdapter implements AuthPort {

    /**
     * userRecordToUserDataMapper.
     */
    private final UserRecordToUserDataMapper userRecordToUserDataMapper;
    /** firebaseTokenToUserDataMapper. */
    private final FirebaseTokenToUserDataMapper firebaseTokenToUserDataMapper;

    /**
     * @inheritDoc.
     */
    public Mono<UserData> verifyToken(final String token) {
        return Mono.fromCallable(() -> {
            try {
                return FirebaseAuth.getInstance().verifyIdToken(token);
            } catch (FirebaseAuthException e) {
                throw new ManagerPromptException(ManagerPromptError.ERROR_AUTHENTICATION_INVALID, HttpStatus.UNAUTHORIZED.value(), e);
            }
        }).map(firebaseTokenToUserDataMapper::transformFirebaseTokenToUserData);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<UserData> getUserData(final String userUid) {
        return Mono.fromCallable(() -> {
            try {
                return FirebaseAuth.getInstance().getUser(userUid);
            } catch (FirebaseAuthException e) {
                throw new ManagerPromptException(ManagerPromptError.ERROR_USER_NOT_FOUND, HttpStatus.NO_CONTENT.value(), e);
            }
        }).map(userRecordToUserDataMapper::transformUserRecordToUserData);
    }

}
