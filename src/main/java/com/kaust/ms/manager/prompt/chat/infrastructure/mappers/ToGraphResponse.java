package com.kaust.ms.manager.prompt.chat.infrastructure.mappers;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToGraphResponse {

    /**
     * Transform graph response to graph response.
     *
     * @param graph {@link BiomedicalGraphResponse}
     * @return {@link GraphResponse}
     */
    GraphResponse toGraphResponse(BiomedicalGraphResponse graph);

}
