package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {FolderDocument.State.class, LocalDateTime.class})
public interface ToFolderDocumentMapper {

    /**
     * Transform folder request to folder document.
     *
     * @param folderRequest {@link FolderRequest}
     * @param userId        {@link String}
     * @return {@link FolderDocument}
     */
    @Mapping(target = "name", source = "folderRequest.name")
    @Mapping(target = "description", source = "folderRequest.description")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "state", expression = "java(FolderDocument.State.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    FolderDocument transformFolderRequestToFolderDocument(FolderRequest folderRequest, String userId);

    /**
     * Transform folder request and folder document to folder document.
     *
     * @param folderDocument {@link FolderDocument}
     * @param folderRequest  {@link FolderRequest}
     * @return {@link FolderDocument}
     */
    @Mapping(target = "id", source = "folderDocument.id")
    @Mapping(target = "name", source = "folderRequest.name")
    @Mapping(target = "description", source = "folderRequest.description")
    @Mapping(target = "userId", source = "folderDocument.userId")
    @Mapping(target = "state", source = "folderDocument.state")
    @Mapping(target = "createdAt", source = "folderDocument.createdAt")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    FolderDocument transformFolderRequestAndFolderDocumentToFolderDocument(FolderDocument folderDocument,
                                                                           FolderRequest folderRequest);

}
