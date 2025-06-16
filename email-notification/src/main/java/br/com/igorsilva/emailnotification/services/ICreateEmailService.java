package br.com.igorsilva.emailnotification.services;

import br.com.igorsilva.emailnotification.entities.EmailModel;

public interface ICreateEmailService {
    EmailModel saveEmail(EmailModel emailModel);

}
