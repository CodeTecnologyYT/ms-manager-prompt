package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface ISendMailSharingChatUseCase {

    /**
     * Send mail to the user with the chat link.
     *
     * @param toEmail {@link String}
     * @param userId {@link String}
     * @param chatId {@link String}
     * @param attachmentBytes {@link Byte[]}
     * @return mono {@link Void}
     */
    Mono<Void> handle(String toEmail, String userId, String chatId, byte[] attachmentBytes);

}
