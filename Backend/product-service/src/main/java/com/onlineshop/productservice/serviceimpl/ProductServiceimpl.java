package com.onlineshop.productservice.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.client.OnlineShopClient;
import com.onlineshop.productservice.constant.Status;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.exception.IdNotFoundException;
import com.onlineshop.productservice.exception.ProductCustomException;
import com.onlineshop.productservice.exception.ProductNotFoundException;
import com.onlineshop.productservice.model.RequestProduct;
import com.onlineshop.productservice.model.RequestUpdateProduct;
import com.onlineshop.productservice.repository.ICategoryRepository;
import com.onlineshop.productservice.repository.IProductMappingRespository;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.service.CategoryService;
import com.onlineshop.productservice.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService {

	private static final Logger logger = LogManager.getLogger(ProductServiceimpl.class);

	@Autowired
	IProductMappingRespository productRepo;

	@Autowired
	ICategoryRepository categoryRepo;

	@Lazy
	@Autowired
	CategoryService categoryService;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	OnlineShopClient onlineShopClient;

	@Transactional
	public Product saveProduct(String requestProduct, MultipartFile file) throws IOException {
		RequestProduct requestCreateProduct = mapper.readValue(requestProduct, RequestProduct.class);

		try {
			
			User useraccount = onlineShopClient.getUserById(requestCreateProduct.getUserId());
			System.out.println(useraccount);
			if (useraccount.getRole().equalsIgnoreCase("seller")) {
				Category category = categoryService.findByCategory(requestCreateProduct.getCategoryId());
				Product product = new Product();
				if (existingProduct(requestCreateProduct.getProductName())) {
					product.setProductName(requestCreateProduct.getProductName());
				} else {
					throw new IdNotFoundException(requestCreateProduct.getProductName() + " already exists. "
							+ " Please add some other new product");
				}
				product.setDescription(requestCreateProduct.getDescription());
				//product.setStocks(requestCreateProduct.getStock());
				product.setStocks(100l);
				product.setSeller(requestCreateProduct.getUserId());
				product.setUnit(requestCreateProduct.getUnit());
				product.setPrice(requestCreateProduct.getPrice());
				product.setAddedAt(LocalDateTime.now());
				product.setCategory(category);
				if(file != null) {
					product.setImageData(file.getBytes());
				}
				
				product.setStatus(Status.APPROVED.name());
				return productRepo.save(product);
			} else {
				throw new ProductCustomException(
						"you are not admin can't add product" + requestCreateProduct.getUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
	}

	public void deletebyId(Long id, Long userId) {
		try {
			User useraccount = onlineShopClient.getUserById(userId);
			Product product = productRepo.findByProductId(id);
			if (useraccount.getRole().equalsIgnoreCase("seller") && product.getSeller() == userId) {
				product.setStatus("DELETED");
				productRepo.save(product);
				//productRepo.delete(product);
			} else {
				throw new ProductCustomException("You dont have permission to delete this product");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}

	}

	public Product updateProduct(String updateReqProduct, MultipartFile file, Long id) throws Exception {
		RequestUpdateProduct requestUpdateProduct = mapper.readValue(updateReqProduct, RequestUpdateProduct.class);
		logger.info("save new product - resquestProduct= {} ", requestUpdateProduct);
		User useraccount = onlineShopClient.getUserById(requestUpdateProduct.getUserId());
		Product product = findByProduct(id);
		if (useraccount.getRole().equalsIgnoreCase("seller")
				&& product.getSeller() == useraccount.getId()) {
			product.setProductName(requestUpdateProduct.getProductName());
			Category category = categoryService.findByCategory(requestUpdateProduct.getCategoryId());
			product.setCategory(category);
			product.setDescription(requestUpdateProduct.getDescription());
			//product.setStocks(requestUpdateProduct.getStock());
			product.setStocks(100l);
			product.setUnit(requestUpdateProduct.getUnit());
			product.setPrice(requestUpdateProduct.getPrice());
			if (file != null) {
				product.setImageData(file.getBytes());
			}
			return productRepo.save(product);
		} else {
			throw new ProductCustomException("You dont have permission to modify this product details");
		}
	}

	public Product getProductById(Long productId) {
		logger.info("get product by id productId={} ", productId);
		try {
			return findByProduct(productId);
		} catch (Exception e) {
			throw new ProductNotFoundException("Product Not found" + productId + e.getMessage());
		}
	}

	public List<ProductResponse> filterbyId(String pName, Long userId) {
		logger.info("Get all  product and filter by category and product name={} checking ", pName);
		try {

			if (pName.isEmpty()) {

				if (isAdmin(userId) || isSeller(userId)) {
					logger.info("Seller id={} ", userId);
					return mapFIlterProduct(productRepo.findAllBySellerOrderByAddedAtDesc(userId));
				}

				return mapFIlterProduct(productRepo.findAllByStatusOrderByAddedAtDesc("APPROVED"));
			} else {
				if (isAdmin(userId) || isSeller(userId)) {
					return mapFIlterProduct(
							productRepo.findBySellerAndProductNameContainingOrCategoryCategoryNameContaining(userId, pName, pName));
				}
				return mapFIlterProduct(
						productRepo.findAllByStatusAndProductNameContainingOrStatusAndCategoryCategoryNameContaining(
								"APPROVED", pName, "APPROVED", pName));
			}

		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}
	}

	public List<ProductResponse> mapFIlterProduct(List<Product> product) {
		return product.stream()
				.map(e -> new ProductResponse(e.getProductId(), e.getProductName(), e.getStocks(), e.getUnit(),
						e.getCategory().getCategoryId(), e.getCategory().getCategoryName(), e.getPrice(),
						e.getImageData(), e.getStatus()))
				.collect(Collectors.toList());

	}

	public boolean isAdmin(Long userId) {
		try {
			User useraccount = onlineShopClient.getUserById(userId);
			return useraccount.getRole().equalsIgnoreCase("admin");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}

	}

	public boolean isSeller(Long userId) {
		try {
			User useraccount = onlineShopClient.getUserById(userId);
			System.out.println(useraccount);
			return useraccount.getRole().equalsIgnoreCase("seller");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}

	}

	public boolean isBuyer(Long userId) {
		try {
			User useraccount = onlineShopClient.getUserById(userId);
			return useraccount.getRole().equalsIgnoreCase("buyer");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}

	}

	public boolean existingProduct(String productName) {
		Product existingProduct = productRepo.findByProductName(productName);
		return existingProduct == null;
	}

	public Product findByProduct(Long productId) {
		return productRepo.findByProductId(productId);
	}

	@Override
	public List<ProductResponse> getAllPendingPoduct() {
		return mapFIlterProduct(productRepo.findAllByStatus(Status.PENDING.name()));
	}

//	public double averageRatings(long productId) {
//		List<Ratings> raitings = ratingRepo.findByProductProductId(productId);
//		List<Double> collect = raitings.stream().map(e -> e.getRating()).collect(Collectors.toList());
//		return collect.stream().mapToDouble(a -> a).average().orElse(0);
//	}

	@Override
	public Product setStatusApproveProduct(Long productId, Long userId, String status) {
		Product product = findByProduct(productId);
		if (isAdmin(userId)) {
			if (status.equalsIgnoreCase("pending")) {
				product.setStatus(Status.PENDING.name());
			} else if (status.equalsIgnoreCase("approved")) {
				product.setStatus(Status.APPROVED.name());
			}
			return productRepo.save(product);
		}
		return null;
	}

//	@Override
//	public Product saveReview(@Valid RequestFeedback feedback) {
//		try {
//			Product product = findByProduct(feedback.getProductId());
//			Feedback fe = feedbackRepo.findByProductProductIdAndBuyerId(feedback.getProductId(),
//					feedback.getUserId());
//			if (fe == null) {
//				fe = new Feedback();
//				User useraccount = onlineShopClient.findByUserIdAndRole(feedback.getUserId(), "buyer");
//				fe.setBuyerId(feedback.getUserId());
//				fe.setProduct(product);
//			}
//
//			fe.setFeedbackContent(feedback.getMessage());
//			feedbackRepo.save(fe);
//			return findByProduct(feedback.getProductId());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ProductCustomException(e.getMessage());
//		}
//
//	}
//
//	@Override
//	public Product saveRating(@Valid RequestRatings rating) {
//		try {
//			Product product = findByProduct(rating.getProductId());
//			Ratings rate = ratingRepo.findByProductProductIdAndBuyerId(rating.getProductId(), rating.getUserId());
//			if (rate == null) {
//				rate = new Ratings();
//				User useraccount = onlineShopClient.findByUserIdAndRole(rating.getUserId(), "buyer");
//				rate.setBuyerId(rating.getUserId());
//				rate.setProduct(product);
//			}
//
//			rate.setRating(rating.getRating());
//			ratingRepo.save(rate);
//			return findByProduct(rating.getProductId());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ProductCustomException(e.getMessage());
//		}
//	}

}
