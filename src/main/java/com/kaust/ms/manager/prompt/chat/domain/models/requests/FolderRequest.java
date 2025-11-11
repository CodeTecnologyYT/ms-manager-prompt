package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Folder Request", description = "Request for folder register",
        example = """
                    {
                        "name": "prueba",
                        "description": "description test"
                    }
                """)
public class FolderRequest {

    /** name. */
    @NotEmpty(message = "name can't is empty")
    @Schema(description = "name folder",requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    /** description. */
    @NotEmpty(message = "description can't is empty")
    @Schema(description = "description folder",requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

}
