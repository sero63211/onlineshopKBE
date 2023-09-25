package com.onlineshop.orderservice.response;

public class OrderResponse {

	private String orderStatus;

	private double Totalamount;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalamount() {
		return Totalamount;
	}

	public void setTotalamount(double totalamount) {
		Totalamount = totalamount;
	}

}
