package com.kaust.ms.manager.prompt.chat.infrastructure.ia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiomedicalRequest {

    /** query to be executed. */
    private String query;
    /** temperature in Celsius. */
    private Integer temperature;

}
