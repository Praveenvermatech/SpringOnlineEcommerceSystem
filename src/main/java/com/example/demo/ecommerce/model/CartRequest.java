package com.example.demo.ecommerce.model;

/**
 * @author praveen.verma
 *
 */
public class CartRequest {

	private long productId;
	private String userId;

	public String getUserId() {
		return userId;
	}

	
	public CartRequest(long productId, String userId) {
		super();
		this.productId = productId;
		this.userId = userId;
	}

}
