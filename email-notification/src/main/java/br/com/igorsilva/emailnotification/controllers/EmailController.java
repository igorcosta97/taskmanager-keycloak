package br.com.igorsilva.emailnotification.controllers;

import br.com.igorsilva.emailnotification.dtos.EmailDTO;
import br.com.igorsilva.emailnotification.entities.EmailModel;
import br.com.igorsilva.emailnotification.facades.EmailFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")

public class EmailController {
    @Autowired
    private EmailFacade emailFacade;
    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/send")
    public ResponseEntity<EmailModel> sendEmail(@RequestBody  EmailDTO emailDTO) {
        log.info("Requisição recebida para enviar um e-mail para: {}", emailDTO.recipient());
        EmailModel emailModel = EmailModel.builder()
                .recipient(emailDTO.recipient())
                .subject(emailDTO.subject())
                .body(emailDTO.body())
                .build();

        emailFacade.createAndSendEmail(emailModel);
        if(emailModel.getStatus().equals("ERRO")) {
            log.error("Erro ao enviar o e-mail para: {}", emailDTO.recipient());
            return ResponseEntity.status(500).body(emailModel);
        }

        log.info("E-mail enviado com sucesso para: {}", emailDTO.recipient());
        return ResponseEntity.ok(emailModel);

    }
}
