package br.com.igorsilva.emailnotification.facades;

import br.com.igorsilva.emailnotification.entities.EmailModel;
import br.com.igorsilva.emailnotification.services.ICreateEmailService;
import br.com.igorsilva.emailnotification.services.ISendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
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

