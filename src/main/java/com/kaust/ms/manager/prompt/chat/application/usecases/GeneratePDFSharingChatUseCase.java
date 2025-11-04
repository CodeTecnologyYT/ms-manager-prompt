package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePDFSharingChatUseCase;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import com.kaust.ms.manager.prompt.report.application.GeneratePDFUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratePDFSharingChatUseCase implements IGeneratePDFSharingChatUseCase {

    /* REPORT_PATH. */
    private final static String REPORT_PATH = "/reports/sharing/chat/report-sharing-chat.jasper";
    /* generatePDFUseCase. */
    private final GeneratePDFUseCase generatePDFUseCase;
    /* chatRepositoryPort. */
    private final MessageRepositoryPort messageRepositoryPort;
    /* getChatByIdUseCase. */
    private final GetChatByIdUseCase getChatByIdUseCase;

    /**
     * Generate a PDF sharing chat.
     *
     * @param chatId {@link String}
     * @param userId {@link String}
     * @return mono {@link Byte}
     */
    @Override
    public Mono<byte[]> handle(final String chatId, final String userId) {
        return getChatByIdUseCase.handle(chatId, userId)
                .flatMap(chat -> messageRepositoryPort.findByChatId(userId, chat.getId())
                        .collectList()
                        .flatMap(messages -> generatePDFUseCase.handle(REPORT_PATH, null, messages)));
    }

}
