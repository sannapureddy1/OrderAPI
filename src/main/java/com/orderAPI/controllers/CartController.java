package com.orderAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderAPI.entities.Cart;
import com.orderAPI.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
//	@PostMapping("/create-cart/{userId}")
//	public Cart createCart(@PathVariable Long userId) {
//		return cartService.createCart(userId);
//	}
	
    /*
      PathVariable - Long {userId}
      RequestParam - String productName
                   - Integer soldQuantity
      RequestURL - http://localhost:9009/cart/add-to-cart/{userId}?productName={value}&soldQuantity={value}
    */
	@PutMapping("/add-to-cart/{userId}")
	public Cart addToCart(@PathVariable Long userId, @RequestParam String productName, @RequestParam(defaultValue = "1") Integer soldQuantity) {
		return cartService.addToCart(userId, productName, soldQuantity);
	}
	
    /*
      PathVariable - Long {userId}
      RequestParam - String productName
      RequestURL - http://localhost:9009/cart/remove-by-name/{userId}?productName={value}
    */
	@PutMapping("/remove-by-name/{userId}")
	public Cart removeFromCart(@PathVariable Long userId, @RequestParam String productName) {
		return cartService.removeFromCart(userId, productName);
	}
	
    /*
      PathVariable - Long {userId}
      RequestURL - http://localhost:9009/cart/remove-all-from-cart/{userId}
    */
	@PutMapping("/remove-all-from-cart/{userId}")
	public Cart removeAllFromCart(@PathVariable Long userId) {
		return cartService.removeAllFromCart(userId);
	}
}
