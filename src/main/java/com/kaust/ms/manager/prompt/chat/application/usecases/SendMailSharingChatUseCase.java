package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetChatByIdUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISendMailSharingChatUseCase;
import com.kaust.ms.manager.prompt.chat.domain.ports.MailManagerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SendMailSharingChatUseCase implements ISendMailSharingChatUseCase {

    /**
     * SUBJECT.
     */
    private final static String SUBJECT_EMAIL = "Sharing Chat";
    /**
     * NAME_TEMPLATE.
     */
    private final static String NAME_TEMPLATE_EMAIL = "sharing-chat-template";
    /**
     * NAME_ATTACHMENT_FILE.
     */
    private final static String NAME_ATTACHMENT_FILE = "sharing-chat-attachment.pdf";
    /**
     * TITLE_SHARING_EMAIL.
     */
    private final static String TEXT_TITLE_SHARING_EMAIL = "A test document was shared with you";
    /**
     * TEXT_ADDITIONAL_INFO.
     */
    private final static String TEXT_ADDITIONAL_INFO = "This is an additional information";
    /**
     * mailManagerPort.
     */
    private final MailManagerPort mailManagerPort;
    /**
     * iGetChatAllByUserIdUseCase.
     */
    private final IGetChatByIdUseCase iGetChatByIdUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Void> handle(final String toEmail, final String userId, final String chatId, final byte[] attachmentFile) {
        return iGetChatByIdUseCase.handle(chatId, userId)
                .flatMap(chat -> {
                    final var variables = new HashMap<String, Object>();
                    variables.put("userName", toEmail);
                    variables.put("chatId", chatId);
                    variables.put("notificationMessage", TEXT_TITLE_SHARING_EMAIL);
                    variables.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    variables.put("additionalInfo", TEXT_ADDITIONAL_INFO);
                    return mailManagerPort.sendEmailWithTemplate(
                            toEmail,
                            SUBJECT_EMAIL,
                            NAME_TEMPLATE_EMAIL,
                            variables,
                            NAME_ATTACHMENT_FILE,
                            attachmentFile
                    );
                });
    }

}
