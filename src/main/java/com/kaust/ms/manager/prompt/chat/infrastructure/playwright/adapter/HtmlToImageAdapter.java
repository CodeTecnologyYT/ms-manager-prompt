package com.kaust.ms.manager.prompt.chat.infrastructure.playwright.adapter;

import com.kaust.ms.manager.prompt.chat.domain.ports.HtmlToImagePort;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HtmlToImageAdapter implements HtmlToImagePort {

    /**
     * @inheritDoc.
     */
    public Mono<byte[]> convertHtmlToImage(String htmlContent) {
        return Mono.fromCallable(() -> {
            try (var playwright = Playwright.create()) {
                // 1. Lanzar navegador ligero (headless = true es por defecto)
                final var browser = playwright.chromium().launch();

                final var context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(800, 600)
                        .setDeviceScaleFactor(2.0));

                final var page = context.newPage();

                // 2. Cargar tu HTML (el que vino del WebClient)
                page.setContent(htmlContent);

                // 3. IMPORTANTE: Esperar a que el JS dibuje el canvas.
                // Si el canvas tiene un ID específico (ej: #network-graph), úsalo aquí.
                // Si no, esperamos un tiempo prudente o a que aparezca la etiqueta canvas.
                page.waitForSelector("#mynetwork canvas", new Page.WaitForSelectorOptions().setTimeout(5000));

                // Opcional: Esperar un poco más para asegurar que la animación terminó
                page.waitForTimeout(1000);

                // 3. HACER CLIC EN EL BOTÓN DE ZOOM PARA CENTRAR TODO
                page.click(".vis-zoomExtends");

                // 4. Esperar a que la animación del zoom termine
                page.waitForTimeout(1000);

                // EJECUTAR JAVASCRIPT EN EL NAVEGADOR PARA OBTENER LA DATA
                String base64 = (String) page.evaluate("""
                    () => {
                        const canvas = document.querySelector('#mynetwork canvas');
                        const context = canvas.getContext('2d');
                        context.globalCompositeOperation = 'destination-over';
                        context.fillStyle = 'white';
                        context.fillRect(0, 0, canvas.width, canvas.height);
                        return canvas.toDataURL('image/png');
                    }
                """);

                browser.close();
                // Decodificar
                String cleanBase64 = base64.split(",")[1];
                return java.util.Base64.getDecoder().decode(cleanBase64);
            }
        });
    }

}
