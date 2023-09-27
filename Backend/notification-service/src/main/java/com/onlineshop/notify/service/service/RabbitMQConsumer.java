package com.onlineshop.notify.service.service;

import javax.mail.internet.MimeMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.notify.service.dto.RequestEmailData;

@Service
public class RabbitMQConsumer {

	@Autowired
	JavaMailSender javaMailSender;

	@RabbitListener(queues = { "${rabbitmq.queue.name}" })
	public void consume(@Payload Message message) {
		System.out.println("==============CONSUMER=============>");
		sendOtpByEmail(message.getBody());
		System.out.println("Message arrived! Message: " + message);
	}

	public void sendOtpByEmail(byte[] data) {
		try {
			
			ObjectMapper mp = new ObjectMapper();
			mp.configure(DeserializationFeature. FAIL_ON_UNKNOWN_PROPERTIES, false);
			RequestEmailData payload = mp.readValue(data, RequestEmailData.class);
			
			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("onlineshop@gmail.com", "OnlineShop");
			helper.setTo(payload.getEmail());

			String subject = "Order placed successfully - ONLINE SHOP";
			String content = "<p>Hello " + payload.getUsername() + ",</p>"
					+ "<p>Thanks for choosing Online Shop. Your order placed successfully - ORDER ID : <b>" + payload.getTransactionId()+"</b></p>"
					+ "<p>TOTAL AMOUNT: <b>" + payload.getTotalAmount() + "USD</b></p>"
					+ "<p>STATUS: <b>" + payload.getPaymentStatus() + "</b></p>"
					+ "<p>PAYMENT VIA : <b>" + payload.getPaymentOption() + "</b></p>";

			helper.setSubject(subject);
			helper.setText(content, true);

			javaMailSender.send(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
