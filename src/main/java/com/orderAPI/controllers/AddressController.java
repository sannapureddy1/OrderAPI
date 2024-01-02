package com.orderAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderAPI.DTO.AddressListResponseDTO;
import com.orderAPI.entities.Address;
import com.orderAPI.services.AddressService;

@RestController
@RequestMapping("address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
    /*
      PathVariable - Long {userId}
	  RequestBody - Address temp
	  RequestURL - http://localhost:9009/address/add-by-id/{userId}
	*/
	@PostMapping("/add-by-id/{userId}")
	public AddressListResponseDTO addAddressById(@PathVariable Long userId, @RequestBody Address temp) {
		return addressService.addAddress(userId, temp);
	}
	
    /*
      PathVariable - String {username}
	  RequestBody - Address temp
	  RequestURL - http://localhost:9009/address/add-by-username/{username}
	*/
	@PostMapping("/add-by-username/{username}")
	public AddressListResponseDTO addAddressByUserName(@PathVariable String username, @RequestBody Address temp) {
		return addressService.addAddress(username, temp);
	}
	
    /*
      PathVariable - Long {userId}
      RequestParam - Long addressId
	  RequestURL - http://localhost:9009/address/remove-by-id/{userId}?addressId={value}
	*/
	@PutMapping("/remove-by-id/{userId}")
	public AddressListResponseDTO removeAddress(@PathVariable Long userId, @RequestParam Long addressId){
		return addressService.removeAddress(userId, addressId);
	}
	
    /*
      PathVariable - String {username}
	  RequestParam - Long addressId
	  RequestURL - http://localhost:9009/address/remove-by-username/{username}?addressId={value}
	*/
	@PutMapping("/remove-by-username/{username}")
	public AddressListResponseDTO removeAddress(@PathVariable String username, @RequestParam Long addressId){
		return addressService.removeAddress(username, addressId);
	}
	
    /*
      PathVariable - Long {userId}
	  RequestURL - http://localhost:9009/address/remove-all/{userId}
	*/
	@PutMapping("/remove-all/{userId}")
	public AddressListResponseDTO removeAllAddresses(@PathVariable Long userId) {
		return addressService.removeAllAddresses(userId);
	}
	
}
