package com.kaust.ms.manager.prompt.settings.infrastructure.mappers;

import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents.SettingsDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = {Model.class})
public interface ToSettingsDocumentMapper {

    /**
     * Transform settings started custom to settings document.
     *
     * @param userId {@link String}
     * @return {@link SettingsDocument}
     */
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "model", expression = "java(Model.AUTO)")
    @Mapping(target = "quantityCreativity", constant = "1.0")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    SettingsDocument transformToStartedSettings(String userId);


    /**
     * Transform settings request to settings a document.
     *
     * @param userId             {@link String}
     * @param settingsDocument   {@link SettingsDocument}
     * @param modelGlobalRequest {@link ModelGlobalRequest}
     * @return {@link SettingsDocument}
     */
    @Mapping(target = "id", source = "settingsDocument.id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "model", source = "modelGlobalRequest.model", qualifiedByName = "toCustomizeModel")
    @Mapping(target = "quantityCreativity", source = "modelGlobalRequest.quantityCreativity")
    @Mapping(target = "createdAt", source = "settingsDocument.createdAt")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    SettingsDocument transformSettingsRequestToSettingsDocument(String userId,
                                                                SettingsDocument settingsDocument,
                                                                ModelGlobalRequest modelGlobalRequest);


    /**
     * Transform a model to a string.
     *
     * @param nameModel {@link Model}
     * @return {@link String}
     */
    @Named("toCustomizeModel")
    static Model toCustomizeModel(String nameModel) {
        return Model.fromName(nameModel);
    }

}
