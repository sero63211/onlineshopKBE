package com.onlineshop.productservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.client.OnlineShopClient;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestProduct;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.service.CategoryService;
import com.onlineshop.productservice.service.ProductService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private ProductService productService;

	@MockBean
	private OnlineShopClient onlineShopClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testCreateProduct() throws Exception {
		Product product = getProduct();
		RequestProduct request = getRequestProduct();
		String writeValueAsBytes = objectMapper.writeValueAsString(request);
		System.out.println("writeValueAsBytes=" + writeValueAsBytes);
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(categoryService.getCategory(Mockito.anyLong())).thenReturn(getCategory());
		Mockito.when(productService.saveProduct(any(String.class), any(MultipartFile.class))).thenReturn(product);
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "filename.txt", "text/plain",
				"some xml".getBytes());
		mockMvc.perform(
				MockMvcRequestBuilders.multipart(HttpMethod.POST, "/product").file("imagefile", firstFile.getBytes())
						.file("data", writeValueAsBytes.getBytes()).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(product)));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Product product = getProduct();
		RequestProduct request = getRequestProduct();
		String writeValueAsBytes = objectMapper.writeValueAsString(request);
		Mockito.when(productService.updateProduct(any(String.class), any(MultipartFile.class), any(Long.class)))
				.thenReturn(product);
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "filename.txt", "text/plain",
				"some xml".getBytes());
		mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/product/" + 1)
				.file("imagefile", firstFile.getBytes()).file("data", writeValueAsBytes.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(product)));
	}

	@Test
	public void testGetProduct() throws Exception {
		Product product = getProduct();
		Mockito.when(productService.getProductById(any(Long.class))).thenReturn(product);
		mockMvc.perform(get("/product/" + 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(product)));
	}

	@Test
	public void testDeleteProduct() throws Exception {
		Mockito.doNothing().when(productService).deletebyId(any(Long.class), any(Long.class));
		mockMvc.perform(delete("/product/" + 1 + "/" + 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testSearchProduct() throws Exception {
		List<ProductResponse> resp = new ArrayList<>();
		ProductResponse request = getProductResponse();
		resp.add(request);
		Mockito.when(productService.filterbyId(any(String.class), any(Long.class)))
				.thenReturn(resp);
		mockMvc.perform(get("/product/search?productName=name&userId=1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(resp)));
	}

//    @Test
//    public void testCreateOrder_InvalidProduct() throws Exception {
//        // Mock the ProductService response
//        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(null);
//
//        // Perform the request
//        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
//                .param("productId", "p2")
//                .param("quantity", "3"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Invalid product ID"));
//
//        // Verify interactions
//        Mockito.verify(productService, Mockito.times(1)).getProductById("p2");
//    }
	private Category getCategory() {
		Category category = new Category();
		category.setCategoryId(1l);
		category.setCategoryName("food");
		category.setStatus("ACTIVE");
		return category;
	}

	private Product getProduct() {
		Product product = new Product();
		product.setAddedAt(LocalDateTime.now());
		product.setCategory(getCategory());
		product.setProductName("Dates");
		product.setDescription("Dates is good for health");
		product.setPrice(20);
		product.setProductId(1l);
		product.setStocks(100l);
		product.setUnit("kg");
		product.setSeller(1l);
		product.setStatus("APPROVED");
		return product;
	}
	
	private ProductResponse getProductResponse() {
		ProductResponse product = new ProductResponse();
		product.setCategoryId(1l);
		product.setProductName("Dates");
		product.setPrice(20);
		product.setStock(100l);
		product.setUnit("kg");
		return product;
	}

	private RequestProduct getRequestProduct() {
		RequestProduct product = new RequestProduct();
		product.setCategoryId(1l);
		product.setProductName("Dates");
		product.setDescription("Dates is good for health");
		product.setPrice(20);
		product.setStock(100l);
		product.setUnit("kg");
		product.setUserId(1l);
		return product;
	}

	private User getUser() {
		User user = new User();
		user.setCreatedAt(LocalDateTime.now());
		user.setEmail("test@gmail.com");
		user.setId(1l);
		user.setName("test_user");
		user.setPhoneNumber("9999988888");
		user.setRole("seller");
		user.setUsername("test_user");
		user.setSurname("user");
		return user;
	}
}
