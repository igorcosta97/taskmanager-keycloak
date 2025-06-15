package br.com.igorsilva.email_notification_service.facades;

import br.com.igorsilva.email_notification_service.entities.EmailModel;
import br.com.igorsilva.email_notification_service.services.ICreateEmailService;
import br.com.igorsilva.email_notification_service.services.ISendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailFacade {

    @Autowired
    private ICreateEmailService createEmailService;

    @Autowired
    private ISendEmailService sendEmailService;

    public EmailModel createAndSendEmail(EmailModel emailModel) {
        EmailModel savedEmail = createEmailService.saveEmail(emailModel);
        return sendEmailService.sendEmail(savedEmail);
    }
}

