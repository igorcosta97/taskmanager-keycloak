package br.com.igorsilva.taskmanager.dtos;


import br.com.igorsilva.taskmanager.enuns.Status;
import jakarta.validation.constraints.NotNull;

public record TaskDto(@NotNull String title, String description, @NotNull Status status) {

}
