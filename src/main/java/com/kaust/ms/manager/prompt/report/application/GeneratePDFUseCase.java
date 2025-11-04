package com.kaust.ms.manager.prompt.report.application;

import com.kaust.ms.manager.prompt.report.domain.ports.ReportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratePDFUseCase implements IGeneratePDFUseCase {

    /* reportPort. */
    private final ReportPort reportPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<byte[]> handle(final String path,final HashMap<String, Object> params, final List<?> data) {
        return reportPort.generatePdfReport(path, params, data);
    }

}
