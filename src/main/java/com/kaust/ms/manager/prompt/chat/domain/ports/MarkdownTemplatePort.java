package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.MarkdownResponse;

import java.util.Map;

public interface MarkdownTemplatePort {

    /**
     * Generate a Markdown template.
     *
     * @param templateName {@link String}
     * @param dataModel {@link Map}
     * @return {@link MarkdownResponse}
     */
    MarkdownResponse process(String templateName, Map<String, Object> dataModel);

}
