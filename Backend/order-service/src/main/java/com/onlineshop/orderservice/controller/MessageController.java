package com.onlineshop.orderservice.controller;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.onlineshop.orderservice.model.RequestEmailData;
import com.onlineshop.orderservice.serviceimpl.RabbitMQProducer;

@RestController
public class MessageController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @PostMapping("/postMessage")
    public String publishMessage(
            @RequestParam("message") String message) throws JsonProcessingException {

    	RequestEmailData email = new RequestEmailData();
		email.setUsername("Test username");
		//email.setCart(findBycart);
		email.setEmail("karthickas.007@gmail.com");
		email.setPayerId("TPayerId");
		email.setPaymentOption("PAYPAL");
		email.setPaymentStatus("SUCCESS");
		email.setTotalAmount(200);
		email.setTransactionId("TTransactionID");
		ObjectMapper ow = new ObjectMapper();
		String json = ow.writeValueAsString(email);
		rabbitTemplate.convertAndSend(exchange,routingKey, json);
       // rabbitMQProducer.sendMessage(message);
        return "Message was sent successfully";
    }
}