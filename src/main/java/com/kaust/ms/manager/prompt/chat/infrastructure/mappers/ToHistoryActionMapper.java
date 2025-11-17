package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {HistoryActionsDocument.ACTION.class, LocalDateTime.class})
public interface ToHistoryActionMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "messageId", source = "messageId")
    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "folderId", source = "folderId")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "quantityCreativity", source = "quantityCreativity")
    @Mapping(target = "numberOfTokens", source = "numberOfTokens")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    HistoryActionsDocument toHistoryActionDocument(String userId,
                                                   String messageId,
                                                   String chatId,
                                                   String folderId,
                                                   HistoryActionsDocument.ACTION action,
                                                   String model,
                                                   Integer quantityCreativity,
                                                   Integer numberOfTokens);

}
