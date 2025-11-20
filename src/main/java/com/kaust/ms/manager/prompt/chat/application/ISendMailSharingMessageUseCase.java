package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface ISendMailSharingMessageUseCase {

    /**
     * Send mail to the user with the message link.
     *
     * @param toEmail {@link String}
     * @param userId {@link String}
     * @param messageId {@link String}
     * @param attachmentFile {@link Byte[]}
     * @return mono {@link Void}
     */
    Mono<Void> handle( String toEmail, String userId, String messageId, byte[] attachmentFile);

}
