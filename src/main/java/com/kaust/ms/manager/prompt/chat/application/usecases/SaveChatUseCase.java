package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetMessageAllByChatIdUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISaveChatUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveChatUseCase implements ISaveChatUseCase {

    /**
     * chatRepositoryPort.
     */
    private final ChatRepositoryPort chatRepositoryPort;
    /** iGetModelGlobalByUserIdUseCase. */
    private final IGetModelGlobalByUserIdUseCase iGetModelGlobalByUserIdUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> handle(final String userId, final ChatRequest chatRequest) {
        return iGetModelGlobalByUserIdUseCase.handle(userId)
                .flatMap(model -> chatRepositoryPort.save(userId, chatRequest, model));
    }

}
