package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.ChatDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToChatResponseMapper {

    /**
     * transform chat document to chat response.
     *
     * @param chatDocument {@link ChatDocument}
     * @return {@link ChatResponse}
     */
    ChatResponse transformChatDocumentToChatResponse(ChatDocument chatDocument);

}
