package pl.lgawin.paypal.ipn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import pl.lgawin.paypal.ipn.data.InMemoryNotificationsRepository;
import pl.lgawin.paypal.ipn.data.NotificationRepository;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    NotificationRepository notificationRepository() {
        return new InMemoryNotificationsRepository();
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
}
