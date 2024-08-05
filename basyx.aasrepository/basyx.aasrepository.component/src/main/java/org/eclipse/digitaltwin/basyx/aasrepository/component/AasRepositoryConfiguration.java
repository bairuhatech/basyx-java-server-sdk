/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package org.eclipse.digitaltwin.basyx.aasrepository.component;

import java.util.List;

import org.eclipse.digitaltwin.basyx.aasrepository.AasRepository;
import org.eclipse.digitaltwin.basyx.aasrepository.AasRepositoryFactory;
import org.eclipse.digitaltwin.basyx.aasrepository.feature.AasRepositoryFeature;
import org.eclipse.digitaltwin.basyx.aasrepository.feature.DecoratedAasRepositoryFactory;
import org.eclipse.digitaltwin.basyx.aasservice.AasService;
import org.eclipse.digitaltwin.basyx.aasservice.AasServiceFactory;
import org.eclipse.digitaltwin.basyx.aasservice.feature.AasServiceFeature;
import org.eclipse.digitaltwin.basyx.aasservice.feature.DecoratedAasServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Provides the spring bean configuration for the {@link AasRepository} and
 * {@link AasService} utilizing all found features for the respective services
 * 
 * @author schnicke
 *
 */
@Configuration
public class AasRepositoryConfiguration {

    /**
     * Creates and returns an instance of {@link AasRepository} with the provided factory and features.
     *
     * @param aasRepositoryFactory the factory to create the AasRepository
     * @param features the list of features to be applied to the repository
     * @return the created AasRepository
     */
    @Bean
    @ConditionalOnMissingBean
    public static AasRepository getAasRepository(AasRepositoryFactory aasRepositoryFactory, List<AasRepositoryFeature> features) {
        return new DecoratedAasRepositoryFactory(aasRepositoryFactory, features).create();
    }

    /**
     * Creates and returns an instance of {@link AasServiceFactory} with the provided factory and features.
     *
     * @param aasServiceFactory the factory to create the AasService
     * @param features the list of features to be applied to the service
     * @return the created AasServiceFactory
     */
    @Primary
    @Bean
    public static AasServiceFactory getAasService(AasServiceFactory aasServiceFactory, List<AasServiceFeature> features) {
        return new DecoratedAasServiceFactory(aasServiceFactory, features);
    }

    /**
     * Configures CORS mappings for the application.
     *
     * @return the WebMvcConfigurer with the CORS configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
}
