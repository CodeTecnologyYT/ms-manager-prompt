package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {Role.class, LocalDateTime.class})
public interface ToMessageDocumentMapper {

    @Mapping(target = "chatId", source = "messageRequest.chatId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "content", source = "messageRequest.content")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    MessageDocument transformMessageRequestToMessageDocument(Role role, String userId,
                                                             MessageRequest messageRequest);


}
