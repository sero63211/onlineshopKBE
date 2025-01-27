package com.onlineshop.orderservice.serviceimpl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

	@Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String message){
    	System.out.println("--------->"+message);
        rabbitTemplate.convertAndSend(exchange,routingKey, message);
        System.out.println("-----return---->");
    }
    
    public void sendEmailMessage(String message){
    	System.out.println("--------->"+message);
        rabbitTemplate.convertAndSend(exchange,routingKey, message);
        System.out.println("-----return---->");
    }
}
