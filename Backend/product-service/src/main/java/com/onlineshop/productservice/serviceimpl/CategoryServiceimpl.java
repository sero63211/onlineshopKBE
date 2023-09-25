package com.onlineshop.productservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.exception.IdNotFoundException;
import com.onlineshop.productservice.exception.ProductCustomException;
import com.onlineshop.productservice.exception.ProductNotFoundException;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.repository.ICategoryRepository;
import com.onlineshop.productservice.service.CategoryService;
import com.onlineshop.productservice.service.ProductService;

@Service
@Component
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	ICategoryRepository categoryRepo;

	@Autowired
	ProductService productservice;

	public Category saveCategory(RequestCategory requestCategory) {
		try {
			if (productservice.isSeller(requestCategory.getUserId())) {
				Category category = new Category();
				category.setCategoryName(requestCategory.getCategoryName());
				return categoryRepo.save(category);
			}

			else {
				throw new ProductCustomException("You are a not a admin");
			}

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException(requestCategory.getCategoryName() + " already exists");
		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}

	}

	public List<Category> findbyOrder(Long userId) {

		try {
			if (productservice.isSeller(userId)) {
				return categoryRepo.findAllByOrderByCategoryNameAsc();
			} else {
				throw new IdNotFoundException("you are not a admin");
			}
		}

		catch (Exception productNotFoundException) {
			throw new ProductNotFoundException(productNotFoundException.getMessage());
		}

	}

	public Category updatecategory(RequestCategory requestCategory, Long id) {

		try {
			if (productservice.isSeller(requestCategory.getUserId())) {
				Category category = findByCategory(id);
				category.setCategoryName(requestCategory.getCategoryName());
				return categoryRepo.save(category);
			} else {
				throw new ProductCustomException("You are not a admin");
			}
		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}

	}

	public void deleteById(Long id, Long userId) {
		if (productservice.isSeller(userId)) {
			Category category = findByCategory(id);
			if (category.getProduct().size() > 0) {
				throw new ProductNotFoundException("Some products are mapped under this category, so cant delete this");
			}

			categoryRepo.deleteById(category.getCategoryId());
		}

	}

	public Category findByCategory(Long id) {
		return categoryRepo.findById(id)
				.orElseThrow(() -> new IdNotFoundException("deleting category id not found " + id));
	}

	public Category getCategory(Long id) {
		try {
			return categoryRepo.findById(id).get();
		}

		catch (Exception productNotFoundException) {
			throw new ProductNotFoundException(productNotFoundException.getMessage());
		}

	}

}
