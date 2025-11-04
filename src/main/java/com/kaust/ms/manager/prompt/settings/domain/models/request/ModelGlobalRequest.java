package com.kaust.ms.manager.prompt.settings.domain.models.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelGlobalRequest {

    /** model. */
    private String model;
    /** quantityCreativity. */
    private Integer quantityCreativity;

}
