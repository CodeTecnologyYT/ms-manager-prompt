package com.kaust.ms.manager.prompt.settings.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Model {

    /** FRONTIER. */
    FRONTIER("Frontier Model"),
    /** FINE_TUNED. */
    FINE_TUNED("Fine Tuned Model"),
    /** AUTO. */
    AUTO("Auto");

    /** name. */
    private final String name;

    /**
     * Por el nombre del modelo.
     *
     * @param name {@link String}
     * @return {@link Model}
     */
    public static Model fromName(String name){
        for(Model model : values()){
            if(model.getName().equals(name)){
                return model;
            }
        }
        return Model.AUTO;
    }
}
