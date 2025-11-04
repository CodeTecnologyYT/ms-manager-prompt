package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import reactor.core.publisher.Mono;

public interface ISaveChatUseCase {

    /**
     * Save chat
     *
     * @param userId      {@link String}
     * @param chatRequest {@link ChatRequest}
     * @return mono {@link Void}
     */
    Mono<ChatResponse> handle(String userId, ChatRequest chatRequest);

}
