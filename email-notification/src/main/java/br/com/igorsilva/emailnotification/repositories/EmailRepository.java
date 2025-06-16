package br.com.igorsilva.emailnotification.repositories;

import br.com.igorsilva.emailnotification.entities.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, String> {

}

