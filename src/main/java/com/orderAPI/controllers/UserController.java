package com.orderAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderAPI.DTO.AddressListResponseDTO;
import com.orderAPI.DTO.UserResponseDTO;
import com.orderAPI.DTO.UserSaveResponse;
import com.orderAPI.DTO.UserUpdateResponse;
import com.orderAPI.entities.Cart;
import com.orderAPI.entities.OrderList;
import com.orderAPI.entities.User;
import com.orderAPI.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	/*
	  PathVariable - none
	  RequestURL - http://localhost:9009/users/get-all
	*/
	@GetMapping("/get-all")
	public List<UserResponseDTO> getAll(){
		return userService.getAll();
	}
	
    /*
	  PathVariable - Long {id}
	  RequestURL - http://localhost:9009/users/get-by-id/{id}
	*/
	@GetMapping("/get-by-id/{id}")
	public UserResponseDTO getById(@PathVariable Long id) {
		return userService.getById(id);
	}
	
    /*
	  PathVariable - String {name}
	  RequestURL - http://localhost:9009/users/get-by-name/{name}
	*/
	@GetMapping("/get-by-name/{username}")
	public UserResponseDTO getByUserName(@PathVariable String username) {
		return userService.getByUserName(username);
	}
	
    /*
	  RequestBody - User temp
	  RequestURL - http://localhost:9009/users/save
	*/
	@PostMapping("/save")
	public UserSaveResponse save(@RequestBody User temp) {
		return userService.save(temp);
	}
	
    /*
      PathVariable - Long {id}
	  RequestBody - User temp
	  RequestURL - http://localhost:9009/users/update/{id}
	*/
	@PutMapping("/update/{id}")
	public UserUpdateResponse update(@PathVariable Long id, @RequestBody User temp) {
		return userService.update(id, temp);
	}
	
    /*
	  PathVariable - Long {id}
	  RequestURL - http://localhost:9009/users/delete-by-id/{id}
	*/
	@DeleteMapping("/delete-by-id/{id}")
	public String deleteById(@PathVariable Long id) {
		return userService.deleteById(id);
	}
	
    /*
	  PathVariable - String {username}
	  RequestURL - http://localhost:9009/users/delete-by-username/{username}
	*/
	@DeleteMapping("/delete-by-username/{username}")
	public String deleteByUserName(@PathVariable String username) {
		return userService.deleteByUserName(username);
	}
	
    /*
	  PathVariable - Long {userId}
	  RequestURL - http://localhost:9009/users/get-all-addresses/{userId}
	*/
	@GetMapping("/get-all-addresses/{userId}")
	public AddressListResponseDTO getAllAddresses(@PathVariable Long userId) {
		return userService.getAllAddresses(userId);
	}
	
    /*
	  PathVariable - Long {userId}
	  RequestURL - http://localhost:9009/users/get-cart-by-id/{userId}
	*/
	@GetMapping("/get-cart-by-id/{userId}")
	public Cart getCart(@PathVariable Long userId) {
		return userService.getCart(userId);
	}
	
    /*
	  PathVariable - Long {userId}
	  RequestURL - http://localhost:9009/users//get-orders-by-id/{userId}
	*/
	@GetMapping("/get-orders-by-id/{userId}")
	public List<OrderList> getOrders(@PathVariable Long userId){
		return userService.getOrders(userId);
	}
	
////	//
//	@GetMapping("/get-order-history/{userId}")
//	public List<OrderList> getOrderHistory(@PathVariable Long userId){
//		return userService.getOrderHistory(userId);
//	}
	
}
