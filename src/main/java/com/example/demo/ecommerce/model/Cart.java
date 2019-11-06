package com.example.demo.ecommerce.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * Cart 
 * @author praveen.verma
 *
 */

@Entity
public class Cart implements Serializable {
		
	private static final long serialVersionUID = 3012325772238219606L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long cartId;
	
	@Column
	private double cartTotal;
	
	@Column
	private String userId;
	
    @JsonIgnore
	@OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE)
	 private List<Products> products;

	
	/**
	 * Constructor
	 */
	public Cart() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param cartId
	 * @param cartTotal
	 * @param userId
	 * @param products
	 */
	public Cart(long cartId, double cartTotal, String userId, List<Products> products) {
		super();
		this.cartId = cartId;
		this.cartTotal = cartTotal;
		this.userId = userId;
		this.products = products;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getCartId() {
		return cartId;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	
	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	
	
	
	

}
