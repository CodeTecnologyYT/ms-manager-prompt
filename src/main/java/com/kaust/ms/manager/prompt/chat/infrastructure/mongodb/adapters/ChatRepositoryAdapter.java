package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.adapters;

import com.google.api.Http;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToChatDocumentMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToChatResponseMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.ChatDocument;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories.ChatRepository;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import com.kaust.ms.manager.prompt.settings.application.IGetModelsUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryAdapter implements ChatRepositoryPort {

    /**
     * chatRepository.
     */
    private final ChatRepository chatRepository;
    /**
     * toChatDocumentMapper.
     */
    private final ToChatDocumentMapper toChatDocumentMapper;
    /**
     * toChatResponseMapper.
     */
    private final ToChatResponseMapper toChatResponseMapper;
    /**
     * iGetModelsUseCase.
     */
    private final IGetModelGlobalByUserIdUseCase iGetModelGlobalByUserIdUseCase;


    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> save(final String userId, final ChatRequest chatRequest, final ModelGlobalResponse model) {
        return chatRepository.save(toChatDocumentMapper
                        .transformMessageRequestToChatDocument(userId, chatRequest, model))
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> findByIdAndByUserId(final String chatId, final String userId) {
        return chatRepository.findByUserIdAndId(userId, chatId)
                .map(toChatResponseMapper::transformChatDocumentToChatResponse)
                .switchIfEmpty(Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_CHAT_NOT_FOUND, HttpStatus.NOT_FOUND.value())));
    }


    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatDocument> findById(final String chatId, final String userId) {
        return chatRepository.findByUserIdAndId(userId, chatId)
                .switchIfEmpty(Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_CHAT_NOT_FOUND, HttpStatus.NOT_FOUND.value())));
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> findByUserIdAndStateActive(final String userId, final Pageable pageable) {
        return chatRepository.findByUserIdAndStateAndFolderIdIsNullOrderByCreatedAtDesc(userId, ChatDocument.State.ACTIVE, pageable)
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Long> countByUserIdAndState(final String userId) {
        return chatRepository.countByUserIdAndState(userId, ChatDocument.State.ACTIVE);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> findByUserIdAndFolderId(final String userId, final String folderId,
                                                      final Pageable pageable) {
        return chatRepository.findByUserIdAndFolderId(userId, folderId, pageable)
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Long> countByUserIdAndFolderId(final String userId, final String folderId) {
        return chatRepository.countByUserIdAndFolderId(userId, folderId);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> update(final String folderId, final ChatDocument chatDocument) {
        return chatRepository.save(toChatDocumentMapper
                        .transformChatDocumentAndFolderToChatDocument(folderId, chatDocument))
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> searchByUserIdAndStateAndTitle(final String userId,
                                                             final String textSearch,
                                                             final Pageable pageable) {
        return chatRepository.searchByUserIdAndStateAndTitle(userId, ChatDocument.State.ACTIVE,
                        textSearch, pageable)
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Long> countSearchByUserIdAndStateAndTitle(final String userId,
                                                          final String textSearch) {
        return chatRepository.countSearchByUserIdAndStateAndTitle(userId,
                ChatDocument.State.ACTIVE,
                textSearch);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> updateModelAndQuantityCreative(final String model, final Double quantityCreative,
                                                             final ChatDocument chatDocument) {
        return chatRepository.save(toChatDocumentMapper.transformChatDocumentToChatDocument(model,
                        quantityCreative, chatDocument))
                .map(toChatResponseMapper::transformChatDocumentToChatResponse);
    }

}
