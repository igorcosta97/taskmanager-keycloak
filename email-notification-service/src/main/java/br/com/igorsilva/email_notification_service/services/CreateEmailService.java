package br.com.igorsilva.email_notification_service.services;

import br.com.igorsilva.email_notification_service.entities.EmailModel;
import br.com.igorsilva.email_notification_service.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailService implements ICreateEmailService {
    @Autowired
    EmailRepository emailRepository;

    @Override
    public EmailModel saveEmail(EmailModel emailModel) {
        emailModel.setStatus("PENDING");
        var email = emailRepository.save(emailModel);
        return email;
    }
}

