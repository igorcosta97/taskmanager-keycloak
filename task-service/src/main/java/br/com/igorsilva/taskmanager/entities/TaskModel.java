package br.com.igorsilva.taskmanager.entities;

import br.com.igorsilva.taskmanager.enuns.Status;
import jakarta.persistence.*;
import lombok.*;
@Table(name = "tasks")
@Entity(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private Status status;
    private String userId;
}
