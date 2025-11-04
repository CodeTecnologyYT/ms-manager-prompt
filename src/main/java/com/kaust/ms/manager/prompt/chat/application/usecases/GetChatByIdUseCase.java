package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetChatByIdUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetChatByIdUseCase implements IGetChatByIdUseCase {

    /** chatRepositoryPort. */
    private final ChatRepositoryPort chatRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> handle(final String chatId, final String userId) {
        return chatRepositoryPort.findByIdAndByUserId(chatId, userId);
    }

}
