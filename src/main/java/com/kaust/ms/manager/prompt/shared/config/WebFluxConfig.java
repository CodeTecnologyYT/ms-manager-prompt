package com.kaust.ms.manager.prompt.shared.config;

import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebFluxConfig implements WebFluxConfigurer {

    /** currentUserArgumentResolver. */
    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    /**
     * @inheritDoc.
     */
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(currentUserArgumentResolver);
    }

}
