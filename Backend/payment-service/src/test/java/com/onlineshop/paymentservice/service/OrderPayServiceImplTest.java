package com.onlineshop.paymentservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.repository.IOrderPayRepository;
import com.onlineshop.paymentservice.serviceimpl.OrderPayServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class OrderPayServiceImplTest {
	
	@InjectMocks
	private OrderPayServiceImpl orderPayService;

	@Mock
	private IOrderPayRepository payRepo;

	@Test
	void testSaveOrderPay() {
		OrderPayment orderPayment = new OrderPayment();
		
		orderPayment.setPaymentOption("paypal");
		orderPayment.setTransactionId("3241212");
		orderPayment.setPayerId("5345323");
		orderPayment.setPaymentStatus("SUCCESS");
		Mockito.when(payRepo.save(any(OrderPayment.class))).thenReturn(orderPayment);
		
		
		RequestOrderPay pay = new RequestOrderPay();
		pay.setPaymentOption("paypal");
		pay.setTransactionId("3241212");
		pay.setPayerId("5345323");
		pay.setPaymentStatus("SUCCESS");
		OrderPayment saveOrderPay = orderPayService.saveOrderPay(pay);
		assertThat(saveOrderPay.getPayerId()).isEqualTo("5345323");
	}

}
