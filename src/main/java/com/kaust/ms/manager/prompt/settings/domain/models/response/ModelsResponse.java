package com.kaust.ms.manager.prompt.settings.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelsResponse {

    /** models. */
    private List<String> models;

}
