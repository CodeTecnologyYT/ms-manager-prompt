package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToFolderResponseMapper {

    /**
     * transform folder document to folder response.
     *
     * @param folderDocument {@link FolderDocument}
     * @return {@link FolderResponse}
     */
    FolderResponse transformFolderDocumentToFolderResponse(FolderDocument folderDocument);

}
