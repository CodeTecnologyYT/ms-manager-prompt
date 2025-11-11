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
@Schema(name = "Mail Request", description = "Request for send mail",
        example = """
                    {
                        "to": "englishbryan.rosas@gmail.com"
                    }
                """)
public class MailRequest {

    /** to. */
    @NotEmpty(message = "Email can't is empty")
    @Schema(description = "email",requiredMode = Schema.RequiredMode.REQUIRED)
    private String to;

}
