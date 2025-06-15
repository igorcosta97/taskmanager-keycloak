package br.com.igorsilva.email_notification_service.services;

import br.com.igorsilva.email_notification_service.entities.EmailModel;
import org.springframework.stereotype.Service;

public interface ICreateEmailService {
    EmailModel saveEmail(EmailModel emailModel);

}
