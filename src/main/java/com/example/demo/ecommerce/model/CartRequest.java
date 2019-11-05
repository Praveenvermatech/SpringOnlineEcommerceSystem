package com.example.demo.ecommerce.model;

/**
 * @author praveen.verma
 *
 */
public class CartRequest {
	
	private long productId;
	private String userId;

	public long getProductId() {
		return productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
	public CartRequest(long productId,  String userId) {
		super();
		this.productId = productId;
		this.userId = userId;
	}
	
	
    
    
    
}
