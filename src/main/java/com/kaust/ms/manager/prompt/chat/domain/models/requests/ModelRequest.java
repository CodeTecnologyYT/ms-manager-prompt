package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Model Request", description = "Request for model register",
        example = """
                    {
                        "model": "Frontier Model",
                        "quantityCreativity": 2
                    }
                """)
public class ModelRequest {

    /** model. */
    @NotEmpty(message = "El modelo no puede estar vacio")
    @Schema(description = "model",requiredMode = Schema.RequiredMode.REQUIRED)
    private String model;
    /** quantityCreativity. */
    @NotEmpty(message = "La cantidad de creatividad no puede estar vacia")
    @Schema(description = "quantityCreativity",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantityCreativity;

}
