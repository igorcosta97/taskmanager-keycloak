package br.com.igorsilva.taskmanager.dtos;

import org.antlr.v4.runtime.misc.NotNull;

public record TaskDto(@NotNull String title, String description, String status) {

}
