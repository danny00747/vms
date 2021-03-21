package be.rentvehicle.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "twilio")
public @Data class TwilioConfiguration {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
