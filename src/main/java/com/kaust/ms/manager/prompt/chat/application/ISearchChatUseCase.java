package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ISearchChatUseCase {

    /**
     * Search chats by text.
     *
     * @param userId {@link String}
     * @param textSearch {@link String}
     * @param pageable {@link Pageable}
     * @return mono page {@link ChatResponse}
     */
    Mono<PageResponse<ChatResponse>> handle(final String userId, final String textSearch, final Pageable pageable);

}
