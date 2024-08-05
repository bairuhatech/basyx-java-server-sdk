package org.eclipse.digitaltwin.basyx.submodelrepository.component;

import java.util.List;

import org.eclipse.digitaltwin.basyx.submodelrepository.SubmodelRepository;
import org.eclipse.digitaltwin.basyx.submodelrepository.SubmodelRepositoryFactory;
import org.eclipse.digitaltwin.basyx.submodelrepository.feature.DecoratedSubmodelRepositoryFactory;
import org.eclipse.digitaltwin.basyx.submodelrepository.feature.SubmodelRepositoryFeature;
import org.eclipse.digitaltwin.basyx.submodelservice.SubmodelService;
import org.eclipse.digitaltwin.basyx.submodelservice.SubmodelServiceFactory;
import org.eclipse.digitaltwin.basyx.submodelservice.feature.DecoratedSubmodelServiceFactory;
import org.eclipse.digitaltwin.basyx.submodelservice.feature.SubmodelServiceFeature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SubmodelRepositoryConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public SubmodelRepository getSubmodelRepository(SubmodelRepositoryFactory aasRepositoryFactory, List<SubmodelRepositoryFeature> features) {
		return new DecoratedSubmodelRepositoryFactory(aasRepositoryFactory, features).create();
	}

	@Primary
	@Bean
	public SubmodelServiceFactory getSubmodelServiceFactory(SubmodelServiceFactory aasServiceFactory, List<SubmodelServiceFeature> features) {
		return new DecoratedSubmodelServiceFactory(aasServiceFactory, features);
	}

}
