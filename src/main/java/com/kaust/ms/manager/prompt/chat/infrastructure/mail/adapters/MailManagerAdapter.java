package com.kaust.ms.manager.prompt.chat.infrastructure.mail.adapters;

import com.kaust.ms.manager.prompt.chat.domain.ports.MailManagerPort;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailManagerAdapter implements MailManagerPort {

    /**
     * mailSender.
     */
    private final JavaMailSender mailSender;
    /**
     * templateEngine.
     */
    private final TemplateEngine templateEngine;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Void> sendEmailWithTemplate(final String to,
                                            final String subject,
                                            final String templateName,
                                            final Map<String, Object> variables,
                                            final String attachmentName,
                                            final byte[] attachmentBytes) {
        return Mono.fromCallable(() -> {
            try {
                // Create the Context with the variables
                final var context = new Context();
                context.setVariables(variables);

                // Proccess template and get HTML content
                final var htmlContent = templateEngine.process("mails/" + templateName, context);

                // Create and send the email
                final var message = mailSender.createMimeMessage();
                final var helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(htmlContent, true);

                // Agree attachment
                helper.addAttachment(attachmentName, new ByteArrayResource(attachmentBytes));

                mailSender.send(message);

                return true;
            } catch (MessagingException e) {
                throw new RuntimeException("Error enviando email", e);
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

}
