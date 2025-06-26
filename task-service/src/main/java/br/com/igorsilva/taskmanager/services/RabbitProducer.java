package br.com.igorsilva.taskmanager.services;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String mensagem) {
        rabbitTemplate.convertAndSend(
                "notification.exchange",
                "notification.key",
                mensagem
        );
    }
}