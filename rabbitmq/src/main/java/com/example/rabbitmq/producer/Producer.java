package com.example.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbitmq.config.ConfigurRabbitMq;

@RestController
public class Producer {

	private final RabbitTemplate rabbitTemplate;
	
	public Producer(RabbitTemplate rabbitTemplate){
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@PostMapping("/send")
	public String sendMessage(@RequestParam String themessage){
		rabbitTemplate.convertAndSend(ConfigurRabbitMq.EXCHANGE_NAME, "my.somemessage", themessage);
		return "I sent a message! : " + themessage;
	}
}
