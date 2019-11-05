package com.example.demo.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.ecommerce.model.Cart;


/***
 * @version 1.0
 * @author praveen.verma
 * CartRepository interface contains the JPA Repository
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

	/**
	 * This method contains find the cart behalf of userId. using the @Query annotation   
	 * @param userId
	 * @return List<Cart>
	 */
	 @Query("select c from Cart c where c.userId= :userId")
	 List<Cart> findByUserIdCart(String userId);

	
}
