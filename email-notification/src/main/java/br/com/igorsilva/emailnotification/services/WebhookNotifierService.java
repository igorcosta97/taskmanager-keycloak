package br.com.igorsilva.emailnotification.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class WebhookNotifierService {

    private static final String WEBHOOK_URL = "https://webhook.site/4a1df676-4ae8-47c5-aa6c-22de6d9b9f3a";

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotification(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"message\":\"%s\"}", message);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(WEBHOOK_URL, request, String.class);

        System.out.println("Resposta do webhook: " + response.getStatusCode());
    }
}
