package com.example.BackEnd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "whatsapp")
public class WhatsAppProperties {

    private boolean enabled = false;
    private String phoneNumberId;
    private String accessToken;
    private String templateName = "hello_world";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(String phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
