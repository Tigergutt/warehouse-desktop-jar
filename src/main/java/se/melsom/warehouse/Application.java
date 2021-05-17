package se.melsom.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import se.melsom.logging.ConfigurationUtility;
import se.melsom.warehouse.application.ApplicationPresentationModel;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ConfigurationUtility.loadConfiguration();

        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);

        ApplicationPresentationModel applicationPresentationModel = context.getBean(ApplicationPresentationModel.class);

        logger.debug("Initialize.");
        applicationPresentationModel.initialize();
        logger.debug("Done.");
    }
}
