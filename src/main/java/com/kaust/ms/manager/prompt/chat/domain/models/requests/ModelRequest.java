package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequest {

    /** model. */
    private String model;
    /** quantityCreativity. */
    private Integer quantityCreativity;

}
