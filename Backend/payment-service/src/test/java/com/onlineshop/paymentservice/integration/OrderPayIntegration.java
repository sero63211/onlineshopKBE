package com.onlineshop.paymentservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.repository.IOrderPayRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderPayIntegration {
	
	@Autowired
	private IOrderPayRepository orderPayRepository;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenStudents_whenGetAllStudents_thenListOfStudents() throws Exception {
		mockMvc.perform( post("/orderPay")
				.content(objectMapper.writeValueAsString(getRequestOrderPay()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	public RequestOrderPay getRequestOrderPay() {
		RequestOrderPay pay = new RequestOrderPay();
		pay.setCartId(1l);
		pay.setUserId(1l);
		pay.setPayerId("21312312");
		pay.setPaymentOption("paypal");
		pay.setPaymentStatus("COMPLETED");
		pay.setTransactionId("324324324");
		return pay;
	}

}
