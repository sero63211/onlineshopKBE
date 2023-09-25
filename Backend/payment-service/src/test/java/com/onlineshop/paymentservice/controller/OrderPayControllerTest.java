package com.onlineshop.paymentservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.service.OrderPayService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderPayController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderPayControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	OrderPayService orderPayService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testUserSignup() throws Exception {
		Mockito.when(orderPayService.saveOrderPay(any(RequestOrderPay.class))).thenReturn(getOrderPayment());
		mockMvc.perform( post("/orderPay")
				.content(objectMapper.writeValueAsString(getRequestOrderPay()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	public OrderPayment getOrderPayment() {
		OrderPayment pay = new OrderPayment();
		pay.setOrderPayId(1l);
		pay.setPayerId("21312312");
		pay.setPaymentOption("paypal");
		pay.setPaymentStatus("COMPLETED");
		pay.setTransactionId("324324324");
		return pay;
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
