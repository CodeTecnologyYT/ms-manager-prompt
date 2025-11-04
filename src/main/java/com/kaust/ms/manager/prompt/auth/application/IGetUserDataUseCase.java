package com.kaust.ms.manager.prompt.auth.application;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import reactor.core.publisher.Mono;
import java.security.Principal;

public interface IGetUserDataUseCase {

    Mono<UserData> handle(String userUid);

}
