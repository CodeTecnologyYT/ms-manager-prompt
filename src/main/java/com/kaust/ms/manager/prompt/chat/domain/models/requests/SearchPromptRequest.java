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
@Schema(name = "Chat Request", description = "Request for search chat",
        example = """
                    {
                        "textSearch": "hola"
                    }
                """)
public class SearchPromptRequest {

    /** textSearch. */
    @NotEmpty(message = "text search can't is empty")
    @Schema(description = "text search",requiredMode = Schema.RequiredMode.REQUIRED)
    private String textSearch;

}
