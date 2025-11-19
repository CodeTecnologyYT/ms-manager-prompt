package com.kaust.ms.manager.prompt.settings.domain.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Model Global Request", description = "Request for model global register",
        example = """
                    {
                        "model": "Frontier Model",
                        "quantityCreativity": 2
                    }
                """)
public class ModelGlobalRequest {

    /** model. */
    @NotEmpty(message = "El modelo no puede estar vacio")
    @Schema(description = "model",requiredMode = Schema.RequiredMode.REQUIRED)
    private String model;
    /** quantityCreativity. */
    @Schema(description = "quantityCreativity",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantityCreativity;

}
