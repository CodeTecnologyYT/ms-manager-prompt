package com.kaust.ms.manager.prompt.settings.infrastructure.mappers;

import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents.SettingsDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ToSettingsResponseMapper {

    /**
     * Transform customize document to customize response.
     *
     * @param settingsDocument {@link SettingsDocument}
     * @return {@link ModelGlobalResponse}
     */
    @Mapping(target = "model", source = "settingsDocument.model", qualifiedByName = "toCustomizeModel")
    ModelGlobalResponse transformCustomizeDocumentToCustomizeResponse(SettingsDocument settingsDocument);

    /**
     * Transform a model to a string.
     *
     * @param model {@link Model}
     * @return {@link String}
     */
    @Named("toCustomizeModel")
    default String toCustomizeModel(Model model) {
        return model.getName();
    }

}
