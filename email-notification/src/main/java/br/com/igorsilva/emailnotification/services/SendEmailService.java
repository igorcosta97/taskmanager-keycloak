package br.com.igorsilva.emailnotification.services;

import br.com.igorsilva.emailnotification.entities.EmailModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService implements ISendEmailService {
    private final JavaMailSender mailSender;
    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public EmailModel sendEmail(EmailModel emailModel) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailModel.getRecipient());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getBody());

        try {
            mailSender.send(message);
            emailModel.setStatus("ENVIADO");
        } catch (Exception e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
            emailModel.setStatus("ERRO");
        }

        return emailModel;
    }
}
