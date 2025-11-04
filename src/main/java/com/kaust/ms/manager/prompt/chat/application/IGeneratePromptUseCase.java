package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import reactor.core.publisher.Mono;


public interface IGeneratePromptUseCase {

    /**
     * Generate prompt.
     *
     * @param messageRequest {@link MessageRequest}
     * @return flux {@link String}
     */
    Mono<String> handle(MessageRequest messageRequest);

}
