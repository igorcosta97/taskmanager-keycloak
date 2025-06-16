package br.com.igorsilva.emailnotification.services;

import br.com.igorsilva.emailnotification.entities.EmailModel;

public interface ISendEmailService {
    EmailModel sendEmail(EmailModel emailModel);
}
