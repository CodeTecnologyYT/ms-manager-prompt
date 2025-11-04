package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ToMessageResponseMapper {

    /**
     * Transform message document to message response.
     *
     * @param messageDocument {@link MessageDocument}
     * @return {@link MessageResponse}
     */
    @Mapping(target = "role", source = "messageDocument.role", qualifiedByName = "toRole")
    MessageResponse transformMessageToMessageResponse(MessageDocument messageDocument);

    /**
     * Convert role to string.
     *
     * @param role {@link Role}
     * @return {@link String}
     */
    @Named("toRole")
    default String toRole(Role role){
        return role.name();
    }

}
