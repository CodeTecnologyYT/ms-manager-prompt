package com.kaust.ms.manager.prompt.report.infrastructure.jasperreport.adapter;

import com.kaust.ms.manager.prompt.report.domain.ports.ReportPort;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JasperReportAdapter implements ReportPort {

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<byte[]> generatePdfReport(String reportPath,
                                          Map<String, Object> parameters,
                                          List<?> dataSource) {
        return Mono.fromCallable(() -> {
            try {

                // Cargar el archivo .jasper compilado
                final var reportStream = new ClassPathResource(reportPath).getInputStream();

                // Crear el data source para JasperReports
                final var jrDataSource = new JRBeanCollectionDataSource(dataSource);

                // Llenar el reporte con los datos
                final var jasperPrint = JasperFillManager.fillReport(reportStream, parameters, jrDataSource);

                // Exportar a PDF
                final var pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

                log.info("Reporte PDF generado exitosamente. Tamaño: {} bytes", pdfBytes.length);

                return pdfBytes;

            } catch (Exception e) {
                throw new ManagerPromptException(ManagerPromptError.ERROR_REPORT_CORRUPT, HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
            }
        });
    }

}
