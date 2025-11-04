package com.kaust.ms.manager.prompt.auth.application;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import reactor.core.publisher.Mono;

public interface IVerifyTokenUserUseCase {

    Mono<UserData> handle(String token);

}
