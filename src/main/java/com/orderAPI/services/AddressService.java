package com.orderAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.DTO.AddressListResponseDTO;
import com.orderAPI.DTO.IMapper;
import com.orderAPI.entities.Address;
import com.orderAPI.entities.User;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.exceptions.IllegalArgumentException;
import com.orderAPI.repositories.AddressRepository;
import com.orderAPI.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private IMapper mapper;
	
	/*
	  POST Methods - addAddress(Long userId, Address temp) - used to add address entities to a user into the database through pathVariable Long userId.
	                                                       - A maximum of 3 addresses have been assigned to each user
	                                                       - requestBody Address temp i.e a request body of type Address must be provided. 
	               - addAddress(String username, Address temp) - used to add address entities to a user into the database through pathVariable String username.
	                                                       - A maximum of 3 addresses have been assigned to each user
	                                                       - requestBody Address temp i.e a request body of type Address must be provided. 
	*/
	
	public AddressListResponseDTO addAddress(Long userId, Address temp) {
		if(!userRepo.existsById(userId)) {
			throw new DataNotFoundException("User with the given id not found");
		}
		if(userId == null || temp.getResidence() == null || temp.getCountry() == null || temp.getState() == null || temp.getCity() == null || temp.getPincode() == null) {
			throw new IllegalArgumentException("Some of the userId, residence, country, state, city or pincode fields are empty");
		}
		User existingUser = userRepo.findById(userId).get();
        if(existingUser.getAddressList().size() > 2) {
        	throw new IllegalArgumentException("Each user can have at most three addresses");
        }
		Address newAddress = new Address(existingUser, temp.getResidence(), temp.getLocality(), temp.getLandmark(), temp.getCountry(), temp.getState(), temp.getCity(), temp.getPincode());
		addressRepo.save(newAddress);
		existingUser.addToAddressList(newAddress);
		return mapper.getAllAddressesByIdResponse(userRepo.save(existingUser));
	}
	
	public AddressListResponseDTO addAddress(String username, Address temp) {
		if(!userRepo.existsByUserNameIgnoreCase(username)) {
			throw new DataNotFoundException("User with the given username not found");
		}
		if(username == null || temp.getResidence() == null || temp.getCountry() == null || temp.getState() == null || temp.getCity() == null || temp.getPincode() == null) {
			throw new IllegalArgumentException("Some of the username, residence, country, state, city or pincode fields are empty");
		}
		User existingUser = userRepo.getByUserNameIgnoreCase(username);
        if(existingUser.getAddressList().size() > 2) {
        	throw new IllegalArgumentException("Each user can have at most three addresses");
        }
		Address newAddress = new Address(existingUser, temp.getResidence(), temp.getLocality(), temp.getLandmark(), temp.getCountry(), temp.getState(), temp.getCity(), temp.getPincode());
		addressRepo.save(newAddress);
		existingUser.addToAddressList(newAddress);
		return mapper.getAllAddressesByIdResponse(userRepo.save(existingUser));
	}
	
	/*
	  PUT Methods - removeAddress(Long userId, Long addressId) - pathVariable Long userId must be passed and a requestParam of type Long addressId to remove one address from the addressList for given user.
	              - removeAddress(String username, Long addressId) - pathVariable String username must be passed and a requestParam of type Long addressId to remove one address from the addressList for given user.
	              - removeAllAddresses() - pathVariable Long userId must be passed to remove all addresses for a given user.
	*/
	
	public AddressListResponseDTO removeAddress(Long userId, Long addressId){
		if(userId == null || addressId == null) {
			throw new IllegalArgumentException("Either the userId or addressId fields is missing");
		}
		if(!userRepo.existsById(userId)) {
			throw new DataNotFoundException("User with the given id not found");
		}
		User existingUser = userRepo.findById(userId).get();
		Address addressToBeDeleted = addressRepo.findById(addressId).get();
		addressToBeDeleted.setUser(null);
		addressRepo.save(addressToBeDeleted);
		existingUser.removeFromAddressList(addressToBeDeleted);
		return mapper.getAllAddressesByIdResponse(userRepo.save(existingUser));
	}
	
	public AddressListResponseDTO removeAddress(String username, Long addressId){
		if(username == null || addressId == null) {
			throw new IllegalArgumentException("Either the username or addressId fields is missing");
		}
		if(!userRepo.existsByUserNameIgnoreCase(username)) {
			throw new DataNotFoundException("User with the given username not found");
		}
		User existingUser = userRepo.getByUserNameIgnoreCase(username);
		Address addressToBeDeleted = addressRepo.findById(addressId).get();
		addressToBeDeleted.setUser(null);
		addressRepo.save(addressToBeDeleted);
		existingUser.removeFromAddressList(addressToBeDeleted);
		return mapper.getAllAddressesByIdResponse(userRepo.save(existingUser));
	}
	
	public AddressListResponseDTO removeAllAddresses(Long userId) {
		if(userId == null) {
			throw new IllegalArgumentException("The userId field is missing");
		}
		if(!userRepo.existsById(userId)) {
			throw new DataNotFoundException("User with the given id not found");
		}
		User existingUser = userRepo.findById(userId).get();
		if(existingUser.getAddressList().isEmpty()) {
			throw new DataNotFoundException("There are no addresses present within the database for the given user, add some addresses and try again");
		}
		for (Address address : existingUser.getAddressList()) {
            address.setUser(null);
            addressRepo.save(address);
        }
		existingUser.getAddressList().removeAll(existingUser.getAddressList());
		return mapper.getAllAddressesByIdResponse(userRepo.save(existingUser));
	}
}
