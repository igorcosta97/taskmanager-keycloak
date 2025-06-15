package br.com.igorsilva.taskmanager.dtos;

public record EmailRequestDTO(String recipient, String subject, String body) {
}
