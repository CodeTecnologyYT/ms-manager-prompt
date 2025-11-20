package com.kaust.ms.manager.prompt.settings.domain.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelGlobalResponse {

    /** id. */
    private String id;
    /** userId. */
    private String userId;
    /** model. */
    private String model;
    /** quantityCreativity. */
    private Double quantityCreativity;

}
