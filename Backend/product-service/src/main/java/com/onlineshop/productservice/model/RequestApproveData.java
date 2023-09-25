package com.onlineshop.productservice.model;

import lombok.Data;

@Data
public class RequestApproveData {
		private Long productId;
		private Long userId;
		private String status;
}
