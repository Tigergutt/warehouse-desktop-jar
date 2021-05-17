package se.melsom.warehouse;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.settings.PersistentSettings;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PersistentSettings persistentSettings() {
        return new PersistentSettings();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelEventBroker modelEventBroker() {
        return new ModelEventBroker();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ApplicationPresentationModel applicationPresentationModel() {
        return new ApplicationPresentationModel();
    }
}
