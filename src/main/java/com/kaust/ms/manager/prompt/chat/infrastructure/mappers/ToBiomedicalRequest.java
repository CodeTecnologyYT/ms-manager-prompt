package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ToBiomedicalRequest {

    @Mapping(target = "query", source = "text")
    @Mapping(target = "temperature", source = "temperature")
    BiomedicalRequest transform(String text, Integer temperature);

}
