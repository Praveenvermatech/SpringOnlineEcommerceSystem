package com.example.demo.ecommerce.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @author praveen.verma 
 * CartController class contains the all API's like : addProductsToCart | getCartDetails | updateProductToCart | deleteProductFromCart | deleteAllProductFromCart
 */

@RestController
@Api(value="Online Ecommerce System")
@RequestMapping("/online-ecommerce/cart")
public class CartController {

	@Autowired
	UserCartServiceImpl userCartService;

	/**
	 * This method contains the add product in cart For each user there will be a separate Cart in the database with unique cart id.
	 * 
	 * @param products
	 * @param userId
	 * @return Products
	 * @throws ResourceNotFoundException 
	 */
	@Transactional
	@PostMapping("/addProductsToCart/{userId}")
	public Products addProductInCart(@RequestBody Products products, @PathVariable String userId) throws ResourceNotFoundException {

		Products product = userCartService.addProductToUserCart(products, userId);
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
	@GetMapping("/getCartDetails/{userId}")
	@Transactional
	public String getCartDetails(@PathVariable String userId) throws ResourceNotFoundException {
		
		String jsonObject = userCartService.getUserCartDetails(userId);
		
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
	@PutMapping("/updateProductToCart/{id}")
	public ResponseEntity<Products> updateProductToCart(@PathVariable(value = "id") Long productId,
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
	@DeleteMapping("/deleteProductFromCart/{id}")
	public String deleteProductFromCart(@RequestBody CartRequest cartRequest, @PathVariable(value = "id") Long productId)
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
	@DeleteMapping("/deleteAllProductFromCart/{id}")
	public String deleteAllProductFromCart(@PathVariable(value = "id") String userId)
			throws ResourceNotFoundException {
			String jsonString = userCartService.deleteAllProductFromUserCart(userId);
			return jsonString;
	}

}
