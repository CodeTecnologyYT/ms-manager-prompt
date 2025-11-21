package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {Role.class, LocalDateTime.class})
public interface ToMessageDocumentMapper {

    @Mapping(target = "chatId", source = "messageRequest.chatId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "content", source = "messageRequest.content")
    @Mapping(target = "messageUser", source = "messageUser")
    @Mapping(target = "entities", source = "chatResponse.entities")
    @Mapping(target = "extractedCandidates", source = "chatResponse.extractedCandidates")
    @Mapping(target = "unmatchedCandidates", source = "chatResponse.unmatchedCandidates")
    @Mapping(target = "triplets", source = "chatResponse.triplets")
    @Mapping(target = "chunkPatterns", source = "chatResponse.chunkPatterns")
    @Mapping(target = "chunks", source = "chatResponse.chunks")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    MessageDocument transformMessageRequestToMessageDocument(Role role, String userId,
                                                             MessageRequest messageRequest,
                                                             BiomedicalChatResponse chatResponse,
                                                             String messageUser);


}
