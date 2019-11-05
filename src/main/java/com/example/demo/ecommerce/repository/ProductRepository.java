package com.example.demo.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.ecommerce.model.Products;

/**
 * @version 1.0
 * @author praveen.verma
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

	/** 
	 * This method contains find all products from cart of given user
	 * @param userId
	 * @return List<Products>
	 */
	@Query("select p from Products p where p.userId= :userId")
	List<Products> findByUserIdProduct(String userId);

}
