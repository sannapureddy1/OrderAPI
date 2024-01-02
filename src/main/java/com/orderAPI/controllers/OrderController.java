package com.orderAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderAPI.entities.OrderList;
import com.orderAPI.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
    /*
      PathVariable - Long {userId}
      RequestURL - http://localhost:9009/order/place-order/{userId}
    */
	@PutMapping("/place-order/{userId}")
	public List<OrderList> placeOrder(@PathVariable Long userId) {
		return orderService.placeOrder(userId);
	}
	
    /*
      PathVariable - Long {userId}
                   - Long {addressId}
      RequestURL - http://localhost:9009/order/place-order/{userId}/{addressId}
    */
	@PutMapping("/place-order-by-address/{userId}/{addressId}")
	public List<OrderList> placeOrderByAddress(@PathVariable Long userId, @PathVariable Long addressId) {
		return orderService.placeOrderByAddress(userId, addressId);
	}
	
    /*
      PathVariable - Long {userId}
                   - Long {addressId}
                   - Long {productId}
      RequestParam - Integer quantity.
      RequestURL - http://localhost:9009/order/place-order/{userId}/{addressId}/{productid}?quantity={value}
    */
	@PutMapping("/place-order-by-product/{userId}/{addressId}/{productId}")
	public OrderList placeOrderByProduct(@PathVariable Long userId, @PathVariable Long addressId, @PathVariable Long productId, @RequestParam(defaultValue = "1") Integer quantity) {
		return orderService.placeOrderByProduct(userId, addressId, productId, quantity);
	}
	
    /*
      PathVariable - Long {userId}
      RequestURL - http://localhost:9009/order/update-status/{orderId}
    */
	@PutMapping("/update-status/{orderId}")
	public List<OrderList> updateOrderStatus(@PathVariable Long orderId){
		return orderService.updateOrderStatus(orderId);
	}
	
}
