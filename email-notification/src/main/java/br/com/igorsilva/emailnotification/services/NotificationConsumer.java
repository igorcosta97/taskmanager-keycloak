package br.com.igorsilva.emailnotification.services;

import br.com.igorsilva.emailnotification.config.RabbitConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final WebhookNotifierService webhookNotifier;
    public NotificationConsumer(WebhookNotifierService webhookNotifier) {
        this.webhookNotifier = webhookNotifier;
    }

    @RabbitListener(queues = RabbitConsumerConfig.QUEUE_NAME)
    public void receiveMessage(String mensagem) {
        webhookNotifier.sendNotification(mensagem);
        System.out.println("Mensagem recebida da fila: " + mensagem);
    }
}
