package br.com.igorsilva.email_notification_service.repositories;

import br.com.igorsilva.email_notification_service.entities.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, String> {

}

