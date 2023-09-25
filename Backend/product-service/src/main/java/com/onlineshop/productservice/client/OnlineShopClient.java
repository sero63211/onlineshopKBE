package com.onlineshop.productservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.dto.User;

@Component
public class OnlineShopClient extends BaseClient<Object> {

	private final Logger logger = LoggerFactory.getLogger(OnlineShopClient.class);

	@Value("${com.app.userById}")
	private String urlByuser;



	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public User getUserById(Long userId) throws JsonMappingException, JsonProcessingException {
		String url = UriComponentsBuilder.fromUriString(urlByuser).pathSegment(userId.toString()).build().toString();
		logger.debug("Generate email for campaign, request payload ={}", url.getClass());
		return convertStringToUser(getRequestForResponse(url, String.class));
		 
	}
	
	public User convertStringToUser(String data) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			System.out.println("Json string data : "+data);
			return objectMapper.readValue(data, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUserIdAndRole(Long userId, String role) throws JsonMappingException, JsonProcessingException {
		String url = UriComponentsBuilder.fromUriString(urlByuser)
				.path("/id-role")
				.pathSegment(userId.toString())
				.pathSegment(role)
				.build().toString();
		logger.debug("Generate email for campaign, request payload ={}", url.getClass());
		return convertStringToUser(getRequestForResponse(url, String.class));
	}
	
	

}
