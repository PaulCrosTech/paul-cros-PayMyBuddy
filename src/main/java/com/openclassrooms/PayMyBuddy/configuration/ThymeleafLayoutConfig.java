package com.openclassrooms.PayMyBuddy.configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ThymeleafLayoutConfig Class
 */
@Configuration
public class ThymeleafLayoutConfig {

    /**
     * Add the layout dialect to the Thymeleaf configuration
     *
     * @return the layout dialect
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
