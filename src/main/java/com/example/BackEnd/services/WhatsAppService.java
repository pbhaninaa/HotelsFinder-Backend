package com.example.BackEnd.services;

import com.example.BackEnd.config.WhatsAppProperties;
import com.example.BackEnd.entities.Booking;
import com.example.BackEnd.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sends WhatsApp messages via Meta WhatsApp Cloud API.
 * For mobile users: no app needed – they receive messages in WhatsApp.
 * Configure whatsapp.enabled, whatsapp.phone-number-id, whatsapp.access-token in application.properties.
 */
@Service
public class WhatsAppService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppService.class);
    private static final String GRAPH_API_BASE = "https://graph.facebook.com/v18.0";

    private final WhatsAppProperties properties;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public WhatsAppService(WhatsAppProperties properties, UserRepository userRepository) {
        this.properties = properties;
        this.userRepository = userRepository;
    }

    /**
     * Send booking confirmation to the guest's WhatsApp (mobile).
     * Runs async so the booking response is not delayed.
     */
    @Async
    public void sendBookingConfirmation(Booking booking) {
        if (!properties.isEnabled() || properties.getPhoneNumberId() == null || properties.getAccessToken() == null) {
            return;
        }
        String phone = normalizePhone(booking.getUserPhone());
        if ((phone == null || phone.isEmpty()) && booking.getUserId() != null) {
            // Fallback: use phone from user profile (e.g. admin added it for testing)
            phone = userRepository.findById(booking.getUserId().intValue())
                    .map(u -> normalizePhone(u.getPhone()))
                    .filter(p -> p != null && !p.isEmpty())
                    .orElse(null);
        }
        if (phone == null || phone.isEmpty()) {
            return;
        }
        try {
            // Use hello_world (no params) or a custom template with body params
            String templateName = properties.getTemplateName() != null ? properties.getTemplateName() : "hello_world";
            if ("hello_world".equals(templateName)) {
                sendTemplate(phone, templateName, List.of());
            } else {
                // Custom template with body params: e.g. "Your booking at {{1}} is confirmed. Check-in: {{2}}, Check-out: {{3}}"
                sendTemplate(phone, templateName, List.of(
                        booking.getHotelName(),
                        booking.getCheckIn() != null ? booking.getCheckIn() : "",
                        booking.getCheckOut() != null ? booking.getCheckOut() : ""
                ));
            }
        } catch (Exception e) {
            log.warn("WhatsApp send failed for booking {}: {}", booking.getId(), e.getMessage());
        }
    }

    /**
     * Send a template message (WhatsApp Cloud API).
     * @param to E.164 phone number (e.g. 27821234567)
     * @param templateName Approved template name in Meta
     * @param bodyParams Optional body parameters in order
     */
    public void sendTemplate(String to, String templateName, List<String> bodyParams) {
        if (to == null || to.isEmpty() || templateName == null || templateName.isEmpty()) {
            return;
        }
        String url = GRAPH_API_BASE + "/" + properties.getPhoneNumberId() + "/messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getAccessToken());

        Map<String, Object> body = buildTemplatePayload(to, templateName, bodyParams);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.warn("WhatsApp API returned {}: {}", response.getStatusCode(), response.getBody());
        }
    }

    private Map<String, Object> buildTemplatePayload(String to, String templateName, List<String> bodyParams) {
        Map<String, Object> template = new HashMap<>();
        template.put("name", templateName);
        template.put("language", Map.of("code", "en_US"));
        if (bodyParams != null && !bodyParams.isEmpty()) {
            List<Map<String, String>> params = new ArrayList<>();
            for (String p : bodyParams) {
                params.add(Map.of("type", "text", "text", p != null ? p : ""));
            }
            Map<String, Object> bodyComponent = new HashMap<>();
            bodyComponent.put("type", "body");
            bodyComponent.put("parameters", params);
            template.put("components", List.of(bodyComponent));
        }
        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("recipient_type", "individual");
        message.put("to", to.replaceAll("[^0-9]", ""));
        message.put("type", "template");
        message.put("template", template);
        return message;
    }

    /** Normalize to E.164: digits only, ensure country code (e.g. 27 for SA). */
    private String normalizePhone(String userPhone) {
        if (userPhone == null) return null;
        String digits = userPhone.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return null;
        if (digits.startsWith("0")) digits = "27" + digits.substring(1);
        else if (!digits.startsWith("27") && digits.length() <= 9) digits = "27" + digits;
        return digits;
    }
}
