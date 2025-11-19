package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.ChatDocument;
import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, ChatDocument.State.class, Model.class})
public interface ToChatDocumentMapper {

    /**
     * Transform message request to chat document.
     *
     * @param chatRequest {@link ChatRequest}
     * @return {@link ChatDocument}
     */
    @Mapping(target = "title", source = "chatRequest.content")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "model", source = "model.model", qualifiedByName = "toModel")
    @Mapping(target = "quantityCreativity", source = "model.quantityCreativity")
    @Mapping(target = "state", expression = "java(ChatDocument.State.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    ChatDocument transformMessageRequestToChatDocument(String userId, ChatRequest chatRequest, ModelGlobalResponse model);

    /**
     * Transform a chat document and folder to a chat document.
     *
     * @param folderId {@link String}
     * @param chatDocument {@link ChatDocument}
     * @return {@link ChatDocument}
     */
    @Mapping(target = "id", source = "chatDocument.id")
    @Mapping(target = "title", source = "chatDocument.title")
    @Mapping(target = "userId", source = "chatDocument.userId")
    @Mapping(target = "state", source = "chatDocument.state")
    @Mapping(target = "model", source = "chatDocument.model")
    @Mapping(target = "quantityCreativity", source = "chatDocument.quantityCreativity")
    @Mapping(target = "folderId", source = "folderId")
    @Mapping(target = "createdAt", source = "chatDocument.createdAt")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    ChatDocument transformChatDocumentAndFolderToChatDocument(String folderId, ChatDocument chatDocument);

    /**
     * Transform a chat document to a chat document.
     *
     * @param model {@link String}
     * @param quantityCreative {@link Integer}
     * @param chatDocument {@link ChatDocument}
     * @return {@link ChatDocument}
     */
    @Mapping(target = "id", source = "chatDocument.id")
    @Mapping(target = "title", source = "chatDocument.title")
    @Mapping(target = "userId", source = "chatDocument.userId")
    @Mapping(target = "state", source = "chatDocument.state")
    @Mapping(target = "model", source = "model", qualifiedByName = "toModel")
    @Mapping(target = "quantityCreativity", source = "quantityCreative")
    @Mapping(target = "folderId", source = "chatDocument.folderId")
    @Mapping(target = "createdAt", source = "chatDocument.createdAt")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    ChatDocument transformChatDocumentToChatDocument(String model, Integer quantityCreative,ChatDocument chatDocument);

    /**
     * Transform a model to a string.
     *
     * @param model {@link Model}
     * @return {@link String}
     */
    @Named("toModel")
    static Model toModel(String model) {
        return Model.fromName(model);
    }


}
