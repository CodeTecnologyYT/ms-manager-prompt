package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface IGeneratePDFSharingChatUseCase {

    /**
     * Generate a PDF sharing chat.
     *
     * @param chatId {@link String}
     * @param userId {@link String}
     * @return mono {@link Byte}
     */
    Mono<byte[]> handle(String chatId, String userId);

}
