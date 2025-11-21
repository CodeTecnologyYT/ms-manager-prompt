package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.MarkdownResponse;
import reactor.core.publisher.Mono;

public interface IGenerateContextViewMDUseCase {

    /**
     * Generate context view md.
     *
     * @param userId {@link String}
     * @param messageId {@link String}
     * @return {@link MarkdownResponse}
     */
    Mono<MarkdownResponse> handle(final String userId, final String messageId);

}
