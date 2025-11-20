package com.kaust.ms.manager.prompt.chat.infrastructure.ia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BiomedicalChatResponse {

    /** answer. */
    private String answer;
    /** entities. */
    private List<Entity> entities;

    /**
     * Entity.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Entity {
        /** id. */
        private String id;
    }
}



