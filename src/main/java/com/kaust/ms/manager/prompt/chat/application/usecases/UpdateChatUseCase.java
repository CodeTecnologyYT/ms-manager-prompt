package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IUpdateChatUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateChatUseCase implements IUpdateChatUseCase {

    /** chatRepositoryPort. */
    private final ChatRepositoryPort chatRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> handle(final String userId, final String folderId, final String chatId) {
        return chatRepositoryPort.findById(chatId, userId)
                .flatMap(chat -> chatRepositoryPort.update(folderId, chat));
    }

}
