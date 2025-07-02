package br.com.igorsilva.emailnotification;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class EmailNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailNotificationApplication.class, args);
	}

}
