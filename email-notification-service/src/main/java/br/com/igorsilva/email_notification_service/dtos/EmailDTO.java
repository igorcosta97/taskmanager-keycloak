package br.com.igorsilva.email_notification_service.dtos;

public record EmailDTO(String recipient, String subject, String body) {
}
