package com.orderAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.DTO.AddressListResponseDTO;
import com.orderAPI.DTO.IMapper;
import com.orderAPI.DTO.UserResponseDTO;
import com.orderAPI.DTO.UserSaveResponse;
import com.orderAPI.DTO.UserUpdateResponse;
import com.orderAPI.entities.Cart;
import com.orderAPI.entities.OrderList;
import com.orderAPI.entities.User;
import com.orderAPI.exceptions.IllegalArgumentException;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.exceptions.DuplicateEntryException;
import com.orderAPI.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private IMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	
	/*
	  GET Methods - getAll() - no parameter required.
	              - getById() - pathVariable Long id must be passed.
	 			  - getByUserName() - pathVariable String userName must be passed.
	 			  - getAllAddresses() - pathVariable Long userId must be passed.
	 			  - getCart() - pathVariable Long userId must be passed.
	 			  - getOrders() - pathVariable Long userId must be passed.
	*/
	
	
	public List<UserResponseDTO> getAll(){
		List<UserResponseDTO> userList = new ArrayList<>();
		for(User x : userRepo.findAll()) {
			userList.add(mapper.convertEntityToDTO(x));
		}
		if(userList.isEmpty()) {
			throw new DataNotFoundException("There are no users present within the database, add few users and try again");
		}
		return userList;
	}
	
	public AddressListResponseDTO getAllAddresses(Long userId){
		if( !userRepo.existsById(userId) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		if(userRepo.findById(userId).get().getAddressList().isEmpty()) {
			throw new DataNotFoundException("There are no addresses present within the database, add an address and try again");
		}
		return mapper.getAllAddressesByIdResponse(userRepo.findById(userId).get());
	}
	
	public Cart getCart(Long userId) {
		if( !userRepo.existsById(userId) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		if(userRepo.findById(userId).get().getCart().getProductList().isEmpty()) {
			throw new DataNotFoundException("There are no product in the cart, add items into the cart and try again");
		}
		return userRepo.findById(userId).get().getCart();
	}
	
	public List<OrderList> getOrders(Long userId){
		if( !userRepo.existsById(userId) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		if(userRepo.findById(userId).get().getOrderList().isEmpty()) {
			throw new DataNotFoundException("There are no orders placed by given user, place an order to use this method");
		}
		return userRepo.findById(userId).get().getOrderList();
	}
	
////	//
//	public List<OrderList> getOrderHistory(Long userId){
//		if( !userRepo.existsById(userId) ) {
//			throw new DataNotFoundException("User with the given id not found");
//		}
//		User temp = userRepo.findById(userId).get();
//		return userRepo.orderHistory(temp.getUserName());
//	}
	
	public UserResponseDTO getById(Long id) {
		if( !userRepo.existsById(id) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		return mapper.convertEntityToDTO(userRepo.findById(id).get());
	}
	
	public UserResponseDTO getByUserName(String userName) {
		if( !userRepo.existsByUserNameIgnoreCase(userName) ) {
			throw new DataNotFoundException("User with the given username not found");
		}
		return mapper.convertEntityToDTO(userRepo.getByUserNameIgnoreCase(userName));
	}
	
	
	/*
	  POST Methods - save() - used to add user entity to the database.
	                       - requestBody User temp i.e a request body of type User must be provided. 
	*/
	
	
	public UserSaveResponse save(User temp) {
		if(userRepo.existsByUserNameIgnoreCase(temp.getUserName())) {
			throw new DuplicateEntryException("User with given username already exists in the database");
		}
		User newUser = new User();
		if(temp.getFirstName() == null || temp.getEmail() == null || temp.getPhoneNumber() == null || temp.getUserName() == null || temp.getPassword() == null) {
			throw new IllegalArgumentException("Some of the firstName, email, phoneNumber, userName or password fields are empty");
		}
		newUser.setFirstName(temp.getFirstName());
		newUser.setLastName(temp.getLastName());
		newUser.setEmail(temp.getEmail());
		newUser.setPhoneNumber(temp.getPhoneNumber());
		newUser.setUsername(temp.getUserName());
		newUser.setPassword(temp.getPassword());
        userRepo.save(newUser);
        cartService.createCart(newUser.getId());
        emailService.sendSimpleMessage(newUser.getEmail(), " Welcome to OrderAPI - Your Account is Ready!" , emailService.accountCreationResponse(newUser));
		return new UserSaveResponse("User with given email and username has been created", newUser.getId(), newUser.getUserName(), newUser.getEmail());
	}
	
	
	/*
	  PUT Methods - update() - pathVariable Long id must be passed and a requestBody of type User is required which consists of String email, Long phoneNumber
	                           String UserName and String Password based on fields that need to be updated.
	*/
	
	
	public UserUpdateResponse update(Long id, User temp) {
		if( !userRepo.existsById(id) ){
			throw new DataNotFoundException("User with the given id not found");
		}
		User updatedUser = userRepo.findById(id).get();
		updatedUser.setEmail(temp.getEmail() != null ? temp.getEmail() : updatedUser.getEmail());
		updatedUser.setPhoneNumber(temp.getPhoneNumber() != null ? temp.getPhoneNumber() : updatedUser.getPhoneNumber());
		updatedUser.setUsername(temp.getUserName() != null ? temp.getUserName() : updatedUser.getUserName());
		updatedUser.setPassword(temp.getPassword() != null ? temp.getPassword() : updatedUser.getPassword());
		return mapper.updateResponse(userRepo.save(updatedUser));
	}
	
	
	/*
	  DELETE Methods - deleteById() - pathVariable Long id must be passed.
	                 - deleteByUserName() - pathVariable String userName must be passed.
	*/
	
	
	public String deleteById(Long id) {
		if( !userRepo.existsById(id) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		userRepo.deleteById(id);
		return "Deleted";
	}
	
	public String deleteByUserName(String userName) {
		if( !userRepo.existsByUserNameIgnoreCase(userName) ) {
			throw new DataNotFoundException("User with the given id not found");
		}
		userRepo.deleteByUserNameIgnoreCase(userName);
		return "Deleted";
	}
	
}
