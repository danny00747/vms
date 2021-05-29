package be.rentvehicle.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SendGrid configuration class
 */
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "sendgrid")
public @Data class SendGridConfiguration {
    private String apiKey;

}
