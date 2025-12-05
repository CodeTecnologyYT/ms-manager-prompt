package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface ISendMailSharingGraphUseCase {

    /**
     * Generate a PDF sharing graph.
     *
     * @param toEmail {@link String}
     * @param userId {@link String}
     * @param messageId {@link String}
     * @param attachmentFile {@link Byte[]}
     * @return {@link Mono}
     */
    Mono<Void> handle(String toEmail, String userId, String messageId, byte[] attachmentFile);

}
