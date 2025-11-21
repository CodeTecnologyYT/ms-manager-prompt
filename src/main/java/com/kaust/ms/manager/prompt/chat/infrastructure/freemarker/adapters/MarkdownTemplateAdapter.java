package com.kaust.ms.manager.prompt.chat.infrastructure.freemarker.adapters;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.MarkdownResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MarkdownTemplatePort;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MarkdownTemplateAdapter implements MarkdownTemplatePort {

    /** configuration. */
    private final Configuration configuration;

    /**
     * @inheritDoc.
     */
    public MarkdownResponse process(String templateName, Map<String, Object> dataModel) {
        try {
            final var template = configuration.getTemplate(templateName);
            final var out = new StringWriter();
            template.process(dataModel, out);
            return new MarkdownResponse(out.toString());
        } catch (Exception e) {
            throw new ManagerPromptException(ManagerPromptError.ERROR_GENERATE_MARKDOWN,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

}
