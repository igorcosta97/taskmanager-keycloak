package br.com.igorsilva.email_notification_service.entities;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "email")
@Entity(name = "email")
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class EmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String recipient;
    private String subject;
    private String body;
    private String status;
}
