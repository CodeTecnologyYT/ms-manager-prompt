package com.kaust.ms.manager.prompt.chat.infrastructure.ia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    /** extractedCandidates. */
    @JsonProperty("extracted_candidates")
    private List<String> extractedCandidates;
    /** unmatchedCandidates. */
    @JsonProperty("unmatched_candidates")
    private List<String> unmatchedCandidates;
    /** triplets. */
    private List<Triplet> triplets;
    /** chuckPattern. */
    @JsonProperty("chunk_patterns")
    private ChuckPattern chunkPatterns;
    /** chucks. */
    private List<Chunk> chunks;


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
        /** name. */
        private String name;
        /** label. */
        private List<String> labels;

    }

    /**
     * Chuck.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Chunk{

        /** chunkId. */
        @JsonProperty("chunk_id")
        private Integer chunkId;
        /** text. */
        private String text;

    }

    /**
     * Triplet.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Triplet {

        /** sourceName. */
        @JsonProperty("source_name")
    	private String sourceName;
        /** sourceProperties. */
        @JsonProperty("source_properties")
        private Properties sourceProperties;
        /** sourceLabels. */
        @JsonProperty("source_labels")
        private List<String> sourceLabels;
        /** relationship. */
        private String relationship;
        /** targetName. */
        @JsonProperty("target_name")
        private String targetName;
        /** targetProperties. */
        @JsonProperty("target_properties")
        private Properties targetProperties;
        /** targetLabels. */
        @JsonProperty("target_labels")
        private List<String> targetLabels;

        /**
         * Properties.
         */
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Properties{

            /** id. */
            private String id;

        }

    }

    /**
     * ChuckPattern.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChuckPattern{

        /** together. */
        private Integer together;
        /** pairs. */
        private Integer pairs;
        /** individual. */
        private Integer individual;

    }

}



