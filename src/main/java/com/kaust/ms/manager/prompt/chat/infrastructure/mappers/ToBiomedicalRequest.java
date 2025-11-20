package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Boolean.class)
public interface ToBiomedicalRequest {

    /**
     * Transform a text to a graph request.
     *
     * @param text        {@link String}
     * @param temperature {@link Double}
     * @return {@link BiomedicalChatRequest}
     */
    @Mapping(target = "query", source = "text")
    @Mapping(target = "temperature", source = "temperature")
    BiomedicalChatRequest transform(String text, Double temperature);

    /**
     * Transform a list of entities to a graph request.
     *
     * @param message {@link MessageDocument}
     * @return {@link BiomedicalGraphRequest}
     */
    @Mapping(target = "entityIds", source = "entities", qualifiedByName = "unionIds")
    @Mapping(target = "degreeLimit", constant = "15")
    @Mapping(target = "minHops", constant = "1")
    @Mapping(target = "maxHops", constant = "2")
    @Mapping(target = "darkMode", expression = "java(Boolean.FALSE)")
    @Mapping(target = "edgeMinWidth", constant = "1")
    @Mapping(target = "edgeMaxWidth", constant = "5")
    BiomedicalGraphRequest transform(MessageDocument message);

    /**
     * Union ids.
     *
     * @param entities {@link BiomedicalChatResponse.Entity}
     * @return {@link List<String>}
     */
    @Named("unionIds")
    default List<String> unionIds(List<BiomedicalChatResponse.Entity> entities) {
        if (Objects.isNull(entities))
            throw new ManagerPromptException(ManagerPromptError.ERROR_ENTITIES_NOT_FOUND, HttpStatus.BAD_REQUEST.value());
        return entities.stream().map(BiomedicalChatResponse.Entity::getId).toList();
    }

}
