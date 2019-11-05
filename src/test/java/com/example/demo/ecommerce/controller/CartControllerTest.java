/**
 * 
 */
package com.example.demo.ecommerce.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.ecommerce.model.Cart;
import com.example.demo.ecommerce.model.CartRequest;
import com.example.demo.ecommerce.model.Products;
import com.example.demo.ecommerce.serviceImpl.UserCartServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author praveen.verma
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

	final int STATUS_CODE_SUCCESSFULLY = 200;
	final String userId = "praveen@hcl.com"; // USER_ID
	final String rootUrl = "/online-ecommerce/cart"; // Root URL
	Products product;
	Cart cart ;
	CartRequest cartRequest;
	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserCartServiceImpl UserCartServiceImpl;
	List<Products> listOfProducts = new ArrayList<Products>();
	
	/**
	 * setUpProduct here | Created fake product Object for given Operations.
	 */
	@Before
	public void setUpProduct() {
		product = new Products();
		product.setProductId(1221);
		product.setProductName("Samsung Glaxy A8+");
		product.setProductAvailability("H");
		product.setProductDescription("6 GB RAM Snapdragan Processor ");
		product.setPrice(32000.00);
		product.setQuantity(8);

		// add product to list
		listOfProducts.add(product);
		// dummy object for mock
		cart = new Cart(1L, 256000.00, userId, listOfProducts);
		
		cartRequest = new CartRequest(product.getProductId(), userId);
		
	}

	/**
	 * Test method for add product in cart
	 * {@link com.example.demo.ecommerce.controller.CartController#addProductInCart(com.example.demo.ecommerce.model.Products, java.lang.String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddProductInCart() throws Exception {

		String requestUrl = "/addProductsToCart";
		String inputJson = convertObjectToJsonString(product);
		// Mock here
		Mockito.when(UserCartServiceImpl.addProductToUserCart(product, userId)).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(rootUrl + "" + requestUrl + "/" + userId)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		// Check STATUS == 200
		assertEquals(STATUS_CODE_SUCCESSFULLY, status); 
		MockHttpServletResponse response = result.getResponse();
		// Check HTTP STATUS is OK
		assertEquals(HttpStatus.OK.value(), response.getStatus()); 
	}

	/**
	 * Test method for get Cart Details
	 * {@link com.example.demo.ecommerce.controller.CartController#getCartDetails(java.lang.String)}.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testGetCartDetails() throws Exception {
		String requestUrl = "/getCartDetails";
		// Mock here
		Mockito.when(UserCartServiceImpl.getUserCartDetails(Mockito.anyString())).thenReturn(convertObjectToJsonString(cart));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(rootUrl + "" + requestUrl + "/" + userId)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("GetCartDetails mock response: " + result.getResponse().getContentAsString());
		// expected and actual output is true
		JSONAssert.assertEquals(convertObjectToJsonString(cart), result.getResponse().getContentAsString(), true);  
		int status = result.getResponse().getStatus();
		// Check STATUS == 200
		assertEquals(STATUS_CODE_SUCCESSFULLY, status); 

		/*
		 * MvcResult mvcResult = mockMvc .perform(MockMvcRequestBuilders.get(rootUrl +
		 * "" + requestUrl + "/" + userId)
		 * .content(asJsonString(product)).contentType(MediaType.APPLICATION_JSON)
		 * .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
		 * .andExpect(status().isOk()).andReturn();
		 * 
		 * int status = mvcResult.getResponse().getStatus(); assertEquals(200, status);
		 */

	}

	/**
	 * Test method for update product to cart.
	 * {@link com.example.demo.ecommerce.controller.CartController#updateProductToCart(java.lang.Long, com.example.demo.ecommerce.model.Products)}.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testUpdateProductToCart() throws Exception {
		String requestUrl = "/updateProductToCart";

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.put(rootUrl + "" + requestUrl + "/" + product.getProductId())
						.content(convertObjectToJsonString(product)).contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		// Check STATUS == 200
		assertEquals(STATUS_CODE_SUCCESSFULLY, status); 
	}

	/**
	 * Test method for delete single product from cart.
	 * {@link com.example.demo.ecommerce.controller.CartController#deleteProductFromCart(com.example.demo.ecommerce.model.CartRequest, java.lang.Long)}.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testDeleteProductFromCart() throws Exception {
		String requestUrl = "/deleteProductFromCart";
		// Mock here
		Mockito.when(UserCartServiceImpl.deleteSingleProductFromUserCart(cartRequest, product.getProductId())).thenReturn(convertObjectToJsonString(cart));		
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.delete(rootUrl + "" + requestUrl + "/" + product.getProductId())
						.content(convertObjectToJsonString(product)).contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		int status = mvcResult.getResponse().getStatus();
		 // Check STATUS == 200
		assertEquals(STATUS_CODE_SUCCESSFULLY, status); 
		System.out.println("Delete product from cart | mock response: " + mvcResult.getResponse().getContentAsString());
	}

	/**
	 * Test method for delete All product from cart.
	 * {@link com.example.demo.ecommerce.controller.CartController#deleteAllProductFromCart(java.lang.String)}.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testDeleteAllProductFromCart() throws Exception {
		String requestUrl = "/deleteAllProductFromCart";
		// Mock here
		Mockito.when(UserCartServiceImpl.deleteAllProductFromUserCart(Mockito.anyString())).thenReturn(convertObjectToJsonString(cart));		
			
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.delete(rootUrl + "" + requestUrl + "/" + userId)
						.content(convertObjectToJsonString(product)).contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		int status = mvcResult.getResponse().getStatus(); 
		// Check STATUS == 200
		assertEquals(STATUS_CODE_SUCCESSFULLY, status); 
		// Expected and actual output is true
		JSONAssert.assertEquals(convertObjectToJsonString(cart), mvcResult.getResponse().getContentAsString(), true);  
	}

	/***
	 * Convert Object to JSON String
	 * 
	 * @param obj
	 * @return String
	 */
	public String convertObjectToJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jsonString to Object
	 * @return Object
	 */
	public static Cart asStringToCartObject(String jsonString) {
		try {
			return new ObjectMapper().readValue(jsonString, Cart.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
