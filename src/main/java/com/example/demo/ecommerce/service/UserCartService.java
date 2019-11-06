package com.example.demo.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.ecommerce.model.CartRequest;
import com.example.demo.ecommerce.model.Products;
import com.example.demo.exception.ResourceNotFoundException;


/**
 * @version 1.0
 * @author Praveen.verma
 *
 */
public interface UserCartService {
	
	/**
	 * @param products
	 * @param userId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Products addProduct(Products products,String userId) throws ResourceNotFoundException;
	
	/**
	 * @param userId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String getDetails(String userId) throws ResourceNotFoundException ;
	
	/**
	 * @param productId
	 * @param product
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public ResponseEntity<Products> updateProductToUserCart(Long productId,	Products product) throws ResourceNotFoundException;
	
	/**
	 * @param cartInfo
	 * @param productId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String deleteSingleProductFromUserCart(CartRequest cartInfo, Long productId)	throws ResourceNotFoundException;
	
	/**
	 * @param userId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String deleteAllProductFromUserCart(String userId)throws ResourceNotFoundException;
	
	
}
