package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IGeneratePromptUseCase {

    /**
     * Generate prompt.
     *
     * @param messageRequest {@link MessageRequest}
     * @return flux {@link String}
     */
    Flux<ChatResponse> handle(MessageRequest messageRequest);

}
