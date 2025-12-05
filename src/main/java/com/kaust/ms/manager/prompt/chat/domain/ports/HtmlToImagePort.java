package com.kaust.ms.manager.prompt.chat.domain.ports;

import reactor.core.publisher.Mono;

public interface HtmlToImagePort {

    /**
     * Convert HTML to image
     *
     * @param htmlContent {@link String}
     * @return {@link Byte[]}
     */
    Mono<byte[]> convertHtmlToImage(String htmlContent);

}
