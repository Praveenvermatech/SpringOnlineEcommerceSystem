package com.example.demo.ecommerce.serviceImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.ecommerce.model.CartRequest;
import com.example.demo.ecommerce.model.Products;
import com.example.demo.exception.ResourceNotFoundException;

/**
 * @version 1.0
 * @author praveen.verma
 * UserCartServiceImplTest class contains the all service implementation test classes.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCartServiceImplTest {

	@Autowired
	UserCartServiceImpl userCartServiceImpl;

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	Products product, anotherProduct, updatedProduct;
	/**
	 * Dummy USER_ID declare here
	 */
	public static String USER_ID = "";

	/**
	 * @throws Exception
	 */
	@Before
	public void setUpProduct() throws Exception {

		USER_ID = "praveen@hcl.com";
		product = new Products();
		product.setProductId(1221);
		product.setProductName("Samsung Glaxy A8+");
		product.setProductAvailability("H");
		product.setProductDescription("6 GB RAM Snapdragan Processor ");
		product.setPrice(32000.00);
		product.setQuantity(8);

		anotherProduct = new Products();
		anotherProduct.setProductId(1222);
		anotherProduct.setProductName("MI 7 Pro");
		anotherProduct.setProductAvailability("L");
		anotherProduct.setProductDescription("4 GB RAM 536 Snapdragan Processor ");
		anotherProduct.setPrice(11500.00);
		anotherProduct.setQuantity(3);

		updatedProduct = new Products();
		updatedProduct.setProductId(1221);
		updatedProduct.setProductName("MI A2");
		updatedProduct.setProductAvailability("L");
		updatedProduct.setProductDescription("6 GB RAM 538 Snapdragan Processor ");
		updatedProduct.setPrice(17500.00);
		updatedProduct.setQuantity(2);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testAddProductToUserCart() throws Exception {

		// Product to be added in cart
		Products productData = userCartServiceImpl.addProductToUserCart(product, USER_ID);
		org.junit.Assert.assertEquals(product.getProductId(), productData.getProductId());
		// Another product added in cart
		Products getProductData = userCartServiceImpl.addProductToUserCart(anotherProduct, USER_ID);
		org.junit.Assert.assertEquals(getProductData.getProductId(), anotherProduct.getProductId());

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testGetUserCartDetails() throws Exception {

		Products getProductData = userCartServiceImpl.addProductToUserCart(anotherProduct, USER_ID);
		org.junit.Assert.assertEquals(getProductData.getProductId(), anotherProduct.getProductId());

		String responseJson = userCartServiceImpl.getUserCartDetails(USER_ID);
		org.junit.Assert.assertEquals(true, responseJson.contains("praveen@hcl.com"));
		
		

	}

	/**
	 * 
	 * @throws ResourceNotFoundException
	 */
	@Test
	public void testUpdateProductToUserCart() throws ResourceNotFoundException {

		// Product to be added in cart
		Products getProductData = userCartServiceImpl.addProductToUserCart(product, USER_ID);
		org.junit.Assert.assertEquals(getProductData.getProductId(), product.getProductId());

		ResponseEntity<Products> productData = userCartServiceImpl.updateProductToUserCart(product.getProductId(),
				updatedProduct);
		org.junit.Assert.assertEquals(200, productData.getStatusCodeValue());

	}

	/**
	 * @throws ResourceNotFoundException
	 */
	@Test
	public void deleteSingleProductFromUserCart() throws ResourceNotFoundException {

		// For Pass Test Case

		CartRequest cartRequest = new CartRequest(product.getProductId(), USER_ID);
		String responseJson = userCartServiceImpl.deleteSingleProductFromUserCart(cartRequest, product.getProductId());
		org.junit.Assert.assertEquals(true, responseJson.contains("cartId"));

		

	}

	/**
	 * @throws ResourceNotFoundException
	 */
	@Test
	public void deleteAllProductFromUserCart() throws ResourceNotFoundException {

		String responseJson = userCartServiceImpl.deleteAllProductFromUserCart(USER_ID);
		org.junit.Assert.assertEquals(true, responseJson.contains("cartId"));
		
		// For Fail Test Case
//		CartRequest cartRequestData = new CartRequest(product.getProductId(), USER_ID);
		String responseJsonData = userCartServiceImpl.deleteAllProductFromUserCart(USER_ID);
		org.junit.Assert.assertEquals(true,  responseJsonData.contains("cartTotal"));
	
		// Fail Test case | Delete all product of given user after that get the cart details.
		
		String responseJsonData2 = userCartServiceImpl.getUserCartDetails(USER_ID);
		org.junit.Assert.assertEquals(true, responseJsonData2.contains("praveen@hcl.com"));
		
				
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
