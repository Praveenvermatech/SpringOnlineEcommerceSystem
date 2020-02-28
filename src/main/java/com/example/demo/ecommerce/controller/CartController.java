package com.example.demo.ecommerce.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ecommerce.model.CartRequest;
import com.example.demo.ecommerce.model.Products;
import com.example.demo.ecommerce.serviceImpl.UserCartServiceImpl;

import com.example.demo.exception.ResourceNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/***
 * @version 1.0
 *
 * CartController class contains the all API's like : addProductsToCart | getCartDetails | updateProductToCart | deleteProductFromCart | deleteAllProductFromCart
 */

@RestController
@Api(value="Online Ecommerce System")
@RequestMapping("/online-ecommerce/cart")
public class CartController {

	private static final Logger logger = Logger.getLogger(CartController.class.getName());
	
	@Autowired
	UserCartServiceImpl userCartService;

	@Autowired
	com.example.demo.Configuration config;
	/**
	 * This method contains the add product in cart For each user there will be a separate Cart in the database with unique cart id.
	 * 
	 * @param products
	 * @param userId
	 * @return Products
	 * @throws ResourceNotFoundException 
	 */
	@Transactional
	@PostMapping(path = "/addProduct/{userId}", 
	consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
	produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public Products addProduct(@RequestBody Products products, @PathVariable String userId) throws ResourceNotFoundException {

		Products product = userCartService.addProduct(products, userId);
		if(product!=null) {
			logger.log(Level.INFO,"@@@ Product add in cart successfully.");   // Logger info implemented here
		}
		return product;
	}

	/**
	 * This method contains the get Cart details and calculate the total price based on the quantity given
	 * 
	 * @param userId
	 * @return Cart
	 * @throws ResourceNotFoundException 
	 */
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved cart details and aadded list of product of this cart"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(path = "/getDetails/{userId}",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Transactional
	public String getDetails(@PathVariable String userId) throws ResourceNotFoundException {
		
		String jsonObject = userCartService.getDetails(userId);
		
		if(!jsonObject.isEmpty()) {
			logger.log(Level.INFO,"Product showing this user.");   // Logger info implemented here
			System.out.println("@@@@ Product showing this user.");
		}else {
			logger.log(Level.INFO,"Product was not for this user.");
			System.out.println("Product was not for this user.@@@@");
		}
		logger.log(Level.INFO,"Product @@@@@@."+jsonObject);  
		logger.log(Level.INFO,"Property from config server ===>."+config.getMaximum());  
		
		return jsonObject;
		
	}
	
	
	/**
	 * This method contains the update product in cart and user should be able to update the quantity of already added product in cart.
	 * 
	 * @param productId
	 * @param updateProduct
	 * @return Products
	 * @throws ResourceNotFoundException
	 */
	@PutMapping(path = "/update/{id}",
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Products> update(@PathVariable(value = "id") Long productId,
			@Valid @RequestBody Products updateProduct) throws ResourceNotFoundException {
		ResponseEntity<Products> productData = userCartService.updateProductToUserCart(productId, updateProduct);
		return productData;
	}
	

	/**
	 * This method contains the delete product from user cart by product id
	 * @param cartRequest 
	 * @param productId
	 * @return jsonString
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping(path = "/delete/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public String delete(@RequestBody CartRequest cartRequest, @PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
			String jsonString = userCartService.deleteSingleProductFromUserCart(cartRequest, productId);
			return jsonString;	
	}
	
	/***
	 * This method contains the delete all product from User cart by user id
	 * @param userId
	 * @return jsonString
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping(path = "/deleteAll/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public String deleteAll(@PathVariable(value = "id") String userId)
			throws ResourceNotFoundException {
			String jsonString = userCartService.deleteAllProductFromUserCart(userId);
			return jsonString;
	}

}
