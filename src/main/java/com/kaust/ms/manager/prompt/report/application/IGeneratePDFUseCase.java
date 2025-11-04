package com.kaust.ms.manager.prompt.report.application;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public interface IGeneratePDFUseCase {

    /**
     * Generate PDF sharing chat.
     *
     * @param path  {@link String}
     * @param params {@link HashMap}
     * @param data   {@link List}
     * @return mono {@link Byte}
     */
    Mono<byte[]> handle(String path, HashMap<String, Object> params, List<?> data);

}
