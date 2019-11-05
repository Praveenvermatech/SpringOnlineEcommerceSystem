package com.example.demo.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ecommerce.repository.ProductRepository;

/***
 * 
 * @author praveen.verma ProductController
 *
 */

@RestController
@RequestMapping("/online-ecommerce")
public class ProductController {

	@Autowired
	ProductRepository productRepository;
		

}
