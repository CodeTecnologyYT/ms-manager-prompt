package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IGeneratePromptUseCase {

    /**
     * Generate prompt.
     *
     * @param messageRequest       {@link MessageRequest}
     * @param userId               {@link String}
     * @param modelChat            {@link String}
     * @param modelChatTemperature {@link Integer}
     * @return flux {@link String}
     */
    Flux<ChatResponse> handle(MessageRequest messageRequest, String userId,
                              String modelChat, Integer modelChatTemperature);

}
