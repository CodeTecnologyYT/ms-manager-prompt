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
public class BiomedicalGraphRequest {

    /** query to be executed. */
    @JsonProperty("entity_ids")
    private List<String> entityIds;
    /** degree limit of the graph. */
    @JsonProperty("degree_limit")
    private Integer degreeLimit;
    /** min hops of the graph. */
    @JsonProperty("min_hops")
    private Integer minHops;
    /** max hops of the graph. */
    @JsonProperty("max_hops")
    private Integer maxHops;
    /** theme of the graph. */
    @JsonProperty("dark_mode")
    private Boolean darkMode;
    /** min width of the edge. */
    @JsonProperty("edge_min_width")
    private Integer edgeMinWidth;
    /** max width of the edge. */
    @JsonProperty("edge_max_width")
    private Integer edgeMaxWidth;

}
