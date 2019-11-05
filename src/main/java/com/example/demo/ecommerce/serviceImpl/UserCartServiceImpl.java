package com.example.demo.ecommerce.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.ecommerce.model.Cart;
import com.example.demo.ecommerce.model.CartRequest;
import com.example.demo.ecommerce.model.Products;
import com.example.demo.ecommerce.repository.CartRepository;
import com.example.demo.ecommerce.repository.ProductRepository;
import com.example.demo.ecommerce.service.UserCartService;
import com.example.demo.ecommerce.utils.ApplicationContext;
import com.example.demo.exception.ResourceNotFoundException;

/**
 * @version 1.0
 * @author praveen.verma
 * This is UserCartService implementation class there are many
 *          service methods like: addProductToUserCart, getUserCartDetails,
 *          updateProductToUserCart, deleteSingleProductFromUserCart,
 *          deleteAllProductFromUserCart
 */
@Service
@Transactional
public class UserCartServiceImpl implements UserCartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public Products addProductToUserCart(Products products, String userId) throws ResourceNotFoundException {
		double totalCartPrice = 0;
		List<Cart> cartList = cartRepository.findByUserIdCart(userId);
		if (cartList.size() > 0) {
			for (Cart cart : cartList) {
				if (cart.getUserId().equalsIgnoreCase(userId)) {

					totalCartPrice = cart.getCartTotal();
					double price = products.getPrice();
					int quantity = products.getQuantity();
					totalCartPrice = totalCartPrice + (price * quantity);
					cart.setCartTotal(totalCartPrice);
					List<Products> productList = cart.getProducts();
					Products newProduct = new Products(products.getProductId(), products.getProductName(),
							products.getPrice(), products.getProductDescription(), products.getProductAvailability(),
							products.getQuantity(), userId, cart);
					productList.add(newProduct);
					cart.setProducts(productList);
					cartRepository.save(cart);
					productRepository.save(newProduct);
					break;
				} else {

				}
			}

		} else {
			Cart cart = new Cart();
			cart.setUserId(userId);
			double price = products.getPrice();
			int quantity = products.getQuantity();
			cart.setCartTotal(price * quantity);
			List<Products> productList = new ArrayList<>();
			Products newProduct = new Products(products.getProductId(), products.getProductName(), products.getPrice(),
					products.getProductDescription(), products.getProductAvailability(), products.getQuantity(), userId,
					cart);
			productList.add(newProduct);
			cart.setProducts(productList);
			cartRepository.save(cart);
			productRepository.save(newProduct);
		}

		return productRepository.getOne(products.getProductId());
	}

	@Override
	public String getUserCartDetails(String userId) throws ResourceNotFoundException {
		List<Cart> cart = cartRepository.findByUserIdCart(userId);
		JSONObject obj = null;
		if (cart.size() > 0) {
			List<Products> productList = cart.get(0).getProducts();
			double cartItemsPrice = 0;

			obj = new JSONObject();
			try {
				obj.put(ApplicationContext.CART_ID, cart.get(0).getCartId());
				obj.put(ApplicationContext.USER_ID, cart.get(0).getUserId());

				if (productList.size() > 0) {
					JSONArray array = new JSONArray();
					for (Products items : productList) {
						JSONObject itemJson = new JSONObject();
						itemJson.put(ApplicationContext.PRODUCT_ID, items.getProductId());
						itemJson.put(ApplicationContext.PRODUCT_NAME, items.getProductName());
						itemJson.put(ApplicationContext.PRODUCT_DESCRIPTION, items.getProductDescription());
						itemJson.put(ApplicationContext.PRODUCT_AVAILABILITY, items.getProductAvailability());
						itemJson.put(ApplicationContext.PRODUCT_QUANTITY, items.getQuantity());
						itemJson.put(ApplicationContext.PRODUCT_PRICE, items.getPrice());
						array.put(itemJson);
						cartItemsPrice = cartItemsPrice + ((items.getPrice()) * items.getQuantity());
					}
					obj.put(ApplicationContext.CART_TOTAL, cartItemsPrice);
					cart.get(0).setCartTotal(cartItemsPrice);
					obj.put(ApplicationContext.CART_ITEMS, array);
				} else {
					obj.put(ApplicationContext.CART_TOTAL, cartItemsPrice);
					cart.get(0).setCartTotal(cartItemsPrice);
					obj.put(ApplicationContext.CART_ITEMS, "[]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// If User id not found then throw Exception Resource Not Found.
			new ResourceNotFoundException(ApplicationContext.USER_NOT_FOUND + " " + userId);
		}
		return obj.toString();
	}

	@Override
	public ResponseEntity<Products> updateProductToUserCart(Long productId, Products product)
			throws ResourceNotFoundException {
		Products productData = productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException(ApplicationContext.PRODUCT_NOT_FOUND + " " + productId));

		// update cart value according to quantity, price, etc
		productData.setProductName(product.getProductName());
		productData.setPrice(product.getPrice());
		productData.setProductDescription(product.getProductDescription());
		productData.setProductAvailability(product.getProductAvailability());
		productData.setQuantity(product.getQuantity());
		productRepository.save(productData);
		Cart cart = productData.getCart();
		List<Products> productList = cart.getProducts();
		cart.setCartTotal(getCartTotalPrice(productList));
		cart.setProducts(productList);
		cartRepository.save(cart);
		return ResponseEntity.ok(productData);
	}

	@Override
	public String deleteSingleProductFromUserCart(CartRequest cartRequest, Long productId)
			throws ResourceNotFoundException {
		JSONObject obj = null;
		// product id is found
		if (productRepository.existsById(productId)) {
			// Delete product here
			productRepository.deleteById(productId);
			
			String userId = cartRequest.getUserId();
			double cartItemsPrice = 0;
			List<Cart> cart = cartRepository.findByUserIdCart(userId);
			cart.get(0).setProducts(productRepository.findByUserIdProduct(userId));
			List<Products> productList = cart.get(0).getProducts();
			obj = new JSONObject();
			try {
				obj.put(ApplicationContext.CART_ID, cart.get(0).getCartId());
				obj.put(ApplicationContext.USER_ID, cart.get(0).getUserId());

				if (productList.size() > 0) {
					JSONArray array = new JSONArray();
					for (Products items : productList) {
						JSONObject itemJson = new JSONObject();
						itemJson.put(ApplicationContext.PRODUCT_ID, items.getProductId());
						itemJson.put(ApplicationContext.PRODUCT_NAME, items.getProductName());
						itemJson.put(ApplicationContext.PRODUCT_DESCRIPTION, items.getProductDescription());
						itemJson.put(ApplicationContext.PRODUCT_AVAILABILITY, items.getProductAvailability());
						itemJson.put(ApplicationContext.PRODUCT_QUANTITY, items.getQuantity());
						itemJson.put(ApplicationContext.PRODUCT_PRICE, items.getPrice());
						array.put(itemJson);
						cartItemsPrice = cartItemsPrice + ((items.getPrice()) * items.getQuantity());
					}
					obj.put(ApplicationContext.CART_TOTAL, cartItemsPrice);
					obj.put(ApplicationContext.CART_ITEMS, array);
				} else {
					obj.put(ApplicationContext.CART_TOTAL, cartItemsPrice);
					obj.put(ApplicationContext.CART_ITEMS, "[]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("JSON: " + obj.toString());

		} else {
			// If product id not found then throw Exception Resource Not Found.
			throw new ResourceNotFoundException(ApplicationContext.PRODUCT_NOT_FOUND + " " + productId);
		}
		return obj.toString();
	}

	@Override
	public String deleteAllProductFromUserCart(String userId) throws ResourceNotFoundException {
		List<Cart> cart = cartRepository.findByUserIdCart(userId);
		JSONObject obj = null;
		if (cart.size() > 0) {
			List<Products> products = productRepository.findByUserIdProduct(userId);
			for (int j = 0; j < products.size(); j++) {
				productRepository.deleteById(products.get(j).getProductId());  // deleted product based on the user
			}
			Cart updatedCartAfterDeleteProducts = getObjectData(cart.get(0));

			cartRepository.save(updatedCartAfterDeleteProducts);
			obj = new JSONObject();
			try {
				obj.put(ApplicationContext.CART_ID, updatedCartAfterDeleteProducts.getCartId());
				obj.put(ApplicationContext.USER_ID, updatedCartAfterDeleteProducts.getUserId());
				obj.put(ApplicationContext.CART_TOTAL, updatedCartAfterDeleteProducts.getCartTotal());
				obj.put(ApplicationContext.CART_ITEMS, "[]");

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("JSON: " + obj.toString());

		} else {
			
			// If User id not found then throw Exception Resource Not Found.
			throw new ResourceNotFoundException(ApplicationContext.USER_NOT_FOUND + " " + userId);
		}
		return obj.toString();
	}

	/**
	 * This method contains calculate the total price based on the quantity given by
	 * user
	 * 
	 * @param productList
	 * @return price otherwise 0.0d
	 */
	private double getCartTotalPrice(List<Products> productList) {
		double price = 0;
		for (Products products : productList) {
			price = price + (products.getPrice() * products.getQuantity());
		}
		return price;
	}

	private Cart getObjectData(Cart cart) {
		cart.setCartId(cart.getCartId());
		cart.setCartTotal(0);
		cart.setProducts(null);
		return cart;
	}

}
