package com.kaust.ms.manager.prompt.chat.domain.ports;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface MailManagerPort {

    /**
     * Send email with a template.
     *
     * @param to {@link String}
     * @param subject {@link String}
     * @param templateName {@link String}
     * @param variables map of variables to be used in the template
     * @param attachmentName {@link String}
     * @param attachmentBytes {@link Byte[]}
     * @return mono {@link Void}
     */
    Mono<Void> sendEmailWithTemplate(String to,
                                     String subject,
                                     String templateName,
                                     Map<String, Object> variables,
                                     String attachmentName,
                                     byte[] attachmentBytes);
}
