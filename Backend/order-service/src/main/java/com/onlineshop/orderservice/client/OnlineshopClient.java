package com.onlineshop.orderservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.orderservice.dto.OrderPayment;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.model.RequestOrderPay;

@Component
public class OnlineshopClient extends BaseClient<Object> {

	private final Logger logger = LoggerFactory.getLogger(OnlineshopClient.class);

	@Value("${com.app.userById}")
	private String urlByuser;
	
	@Value("${com.app.productId}")
	private String urlByProduct;
	
	@Value("${com.app.orderPayment}")
	private String urlByPayment;
	

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public User getUserById(Long userId) throws JsonMappingException, JsonProcessingException {
		String url = UriComponentsBuilder.fromUriString(urlByuser).pathSegment(userId.toString()).build().toString();
		logger.debug("Generate email for campaign, request payload ={}", url.getClass());
		return getRequestForResponse(url, User.class);
		 
	}
	public Product getProductById(Long userId) throws JsonMappingException, JsonProcessingException {
		String url = UriComponentsBuilder.fromUriString(urlByProduct).pathSegment(userId.toString()).build().toString();
		logger.debug("Generate email for campaign, request payload ={}", url.getClass());
		return getRequestForResponseProduct(url, Product.class);
		 
	}
	
	public OrderPayment createOrderPayment(RequestOrderPay orderPay)  {
		String url = UriComponentsBuilder.fromUriString(urlByPayment).build().toString();
		String requestPayload = convertOrderPaymentToString(orderPay);
		logger.debug("Generate email for campaign, request payload ={}", requestPayload);
		 
		 
		return postRequestForResponse(url, requestPayload, OrderPayment.class);
		 
	}

	private String convertOrderPaymentToString(RequestOrderPay orderPay) {
		try {
			return objectMapper.writeValueAsString(orderPay);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			 
		}
		 return null;
	}
	
		

}
