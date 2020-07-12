package com.example.rabbitmq.consumer;

import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
	public void handleMessage(String message){
		System.out.println("Handle message: " + message);
	}
}
