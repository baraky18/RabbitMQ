package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.rabbitmq.consumer.Consumer;

@Configuration
public class ConfigurRabbitMq {

	public static final String EXCHANGE_NAME = "myexchange";
	public static final String QUEUE_NAME = "myqueue";

	/*
	 * creating a queue
	 */
	@Bean
	Queue createQueue(){
		return new Queue(QUEUE_NAME, false);
	}
	
	/*
	 * creating an exchange
	 */
	@Bean
	TopicExchange createExchange(){
		return new TopicExchange(EXCHANGE_NAME);
	}
	
	/*
	 * bind queue to exchange and add the routing key 
	 */
	@Bean
	Binding binding(Queue q, TopicExchange exchange){
		return BindingBuilder.bind(q).to(exchange).with("my.#");
	}
	
	/*
	 * set the listener to listen to the queue that we want
	 */
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter adapter){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(adapter);
		return container;
	}
	
	/*
	 * set the listener to invoke the consumer method each time correct message is fired
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(Consumer consumer){
		return new MessageListenerAdapter(consumer, "handleMessage");
	}
}
