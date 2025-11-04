package com.kaust.ms.manager.prompt.report.domain.ports;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ReportPort {

    /**
     * Genera un reporte en formato PDF
     * @param reportPath Ruta del archivo .jasper (ejemplo: "reports/sharing/chat/report-sharing-chat.jasper")
     * @param parameters Parámetros del reporte
     * @param dataSource Lista de objetos para el reporte
     * @return Mono con el reporte en bytes
     */
    Mono<byte[]> generatePdfReport(String reportPath,
                                   Map<String, Object> parameters,
                                   List<?> dataSource);

}
