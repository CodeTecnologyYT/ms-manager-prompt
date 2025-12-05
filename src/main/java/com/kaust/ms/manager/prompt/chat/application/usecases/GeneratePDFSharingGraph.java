package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePDFSharingGraph;
import com.kaust.ms.manager.prompt.chat.domain.ports.HtmlToImagePort;
import com.kaust.ms.manager.prompt.report.application.GeneratePDFUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratePDFSharingGraph implements IGeneratePDFSharingGraph {

    /**
     * REPORT_PATH.
     */
    private final static String REPORT_PATH = "/reports/sharing/graph/report-sharing-graph.jasper";
    /**
     * generatePDFUseCase.
     */
    private final GeneratePDFUseCase generatePDFUseCase;
    /**
     * htmlToImagePort.
     */
    private final HtmlToImagePort htmlToImagePort;
    /**
     * graphEntitiesUseCase.
     */
    private final GenerateGraphEntitiesUseCase graphEntitiesUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<byte[]> handle(final String messageId, final String userId) {

        return graphEntitiesUseCase.handle(messageId, userId)
                .flatMap(graphResponse -> htmlToImagePort.convertHtmlToImage(graphResponse.getHtml()))
                .flatMap(imageBytes -> {
                    final var params = new HashMap<String, Object>();
                    params.put("IMAGE_GRAPH", new ByteArrayInputStream(imageBytes));
                    return generatePDFUseCase.handle(REPORT_PATH, params, null);
                });
    }


}
