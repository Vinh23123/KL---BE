package KL.KL_Booking_App.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailJetConfig {
    @Value("${vinh.app.mailjet.api-key}")
    private String apiKey;

    @Value("${vinh.app.mailjet.secret-key}")
    private String apiSecret;

    public MailJetConfig(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public MailJetConfig() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}
