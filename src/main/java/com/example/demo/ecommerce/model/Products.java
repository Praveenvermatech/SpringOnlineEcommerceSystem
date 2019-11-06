package com.example.demo.ecommerce.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/***
 * Products
 * @author praveen.verma
 *
 */

@Entity
@Table(name = "Products")
public class Products implements Serializable{

	/**
	 * serial version id here
	 */
	private static final long serialVersionUID = 6870511424065901398L;
	@Id
	@Column(nullable = false)
	private long productId;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String productDescription;
	@Column(nullable = false)
	private String productAvailability;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private String userId;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Cart cart;
	
	/**
	 * Constructor
	 */
	public Products() {
		// TODO Auto-generated constructor stub
	}
	
	
			
	/**
	 * @param productId
	 * @param productName
	 * @param price
	 * @param productDescription
	 * @param productAvailability
	 * @param quantity
	 * @param userId
	 * @param cart
	 */
	public Products(long productId, String productName, double price, String productDescription,
			String productAvailability, int quantity, String userId, Cart cart) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.productDescription = productDescription;
		this.productAvailability = productAvailability;
		this.quantity = quantity;
		this.userId = userId;
		this.cart = cart;
	}

	public long getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}
	public double getPrice() {
		return price;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public String getProductAvailability() {
		return productAvailability;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public void setProductAvailability(String productAvailability) {
		this.productAvailability = productAvailability;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	

	
}
