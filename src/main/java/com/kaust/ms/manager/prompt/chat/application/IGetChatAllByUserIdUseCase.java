package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IGetChatAllByUserIdUseCase {

    /**
     * Get chat by user id.
     *
     * @param userId {@link String}
     * @return mono page {@link ChatResponse}}
     */
    Mono<PageResponse<ChatResponse>> handle(String userId, Pageable pageable);

}
