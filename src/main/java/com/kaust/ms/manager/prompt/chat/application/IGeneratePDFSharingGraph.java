package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface IGeneratePDFSharingGraph {

    /**
     * Generate a PDF sharing graph.
     *
     * @param messageId {@link String}
     * @param userId {@link String}
     * @return mono {@link Byte}
     */
    Mono<byte[]> handle(String messageId, String userId);

}
