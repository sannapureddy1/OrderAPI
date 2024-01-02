package com.orderAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.entities.Address;
import com.orderAPI.entities.CartInventory;
import com.orderAPI.entities.Inventory;
import com.orderAPI.entities.Order;
import com.orderAPI.entities.OrderList;
import com.orderAPI.entities.User;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.exceptions.IllegalArgumentException;
import com.orderAPI.enumerations.OrderStatus;
import com.orderAPI.repositories.InventoryRepository;
import com.orderAPI.repositories.OrderListRepository;
import com.orderAPI.repositories.OrderRepository;
import com.orderAPI.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrderListRepository orderListRepo;
	
	@Autowired
	private InventoryRepository inventoryRepo;
	
	/*
	  PUT Methods - placeOrder - pathVariable Long userId must be passed.
	              - placeOrderByAddress - pathVariables Long userId and Long addressId must be passed.
	              - placeOrderByProduct - pathVariables Long userId, Long addressId and Long productId must be passed.
	                                    - requestParam quantity is required to implement the general use-case.
	              - updateOrderStatus - pathVariable orderId must be passed to toggle the OrderStatus.
	*/
  
	public List<OrderList> placeOrder(Long userId) {
		if(userId == null) {
			throw new IllegalArgumentException("The userId field is empty");
		}
		if(!userRepo.existsById(userId)) {
			throw new DataNotFoundException("User with the given id not found");
		}
		User user = userRepo.findById(userId).get();
		List<CartInventory> productList = user.getCart().getProductList();
		if(productList.isEmpty()) {
			throw new DataNotFoundException("The cart for the given user is empty");
		}
		else {
			Order order = new Order();
			order.setUsername(user.getUserName());
			List<CartInventory> soldItemsFromCart = new ArrayList<>();
			for(CartInventory x : productList) {
				OrderList orderList = new OrderList();
				orderList.setUser(user);
				orderList.setUsername(user.getUserName());
				orderList.setShippingAddress(user.getAddressList().get(0));
				orderList.setPayableAmount(x.getTotalUnitPrice());
				orderList.setOrder(order);
				orderList.setProduct(x.getInventory());
				orderList.setProductName(x.getProductName());
				orderList.setStatus(OrderStatus.ORDER_PLACED);
				orderListRepo.save(orderList);
				soldItemsFromCart.add(x);
				order.getOrderList().add(orderList);
			}
			user.getCart().getProductList().removeAll(soldItemsFromCart);
			user.getCart().calculateTotal();
		    userRepo.save(user);
			orderRepo.save(order);
			return user.getOrderList();
		}
	}
	
	public List<OrderList> placeOrderByAddress(Long userId, Long addressId) {
		if(userId == null || addressId == null) {
			throw new IllegalArgumentException("The userId or addressId field is empty");
		}
		if(!userRepo.existsById(userId)) {
			throw new DataNotFoundException("User with the given id not found");
		}
		User user = userRepo.findById(userId).get();
		List<CartInventory> productList = user.getCart().getProductList();
		Address shippingAddress = findAddress(userId,addressId);
		if(productList.isEmpty() || shippingAddress == null) {
			throw new DataNotFoundException("The cart for the given user is empty or The address with the given id is not found");
		}
		else {
			Order order = new Order();
			order.setUsername(user.getUserName());
			List<CartInventory> soldItemsFromCart = new ArrayList<>();

			for(CartInventory x : productList) {
				OrderList orderList = new OrderList();
				orderList.setUser(user);
				orderList.setUsername(user.getUserName());
				orderList.setShippingAddress(shippingAddress);
				orderList.setPayableAmount(x.getTotalUnitPrice());
				orderList.setOrder(order);
				orderList.setProduct(x.getInventory());
				orderList.setProductName(x.getProductName());
				orderList.setStatus(OrderStatus.ORDER_PLACED);
				orderListRepo.save(orderList);
				soldItemsFromCart.add(x);
				order.getOrderList().add(orderList);
			}
			user.getCart().getProductList().removeAll(soldItemsFromCart);
			user.getCart().calculateTotal();
		    userRepo.save(user);
			orderRepo.save(order);
			return user.getOrderList();
		}
	}
	
	private Address findAddress(Long userId, Long addressId) {
		if(addressId == null) {
			return null;
		}
		List<Address> AddressList = userRepo.findById(userId).get().getAddressList();
		for(Address x : AddressList) {
			if(x.getId() == addressId) {
				return x;
			}
		}
		return null;
	}
	
	public OrderList placeOrderByProduct(Long userId, Long addressId, Long productId, Integer quantity) {
		if(userId == null || addressId == null || productId == null) {
			throw new IllegalArgumentException("The userId, addressId or productId fields are empty");
		}
		User user = userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException("User with the given id not found"));
		Inventory product = inventoryRepo.findById(productId).orElseThrow(() -> new DataNotFoundException("Product with the given id not found"));
		Address shippingAddress = findAddress(userId,addressId);
		if(shippingAddress == null) {
			throw new DataNotFoundException("Address with the given id not found");
		}
		Order order = new Order();
		order.setUsername(user.getUserName());
			
		OrderList orderItem = new OrderList();
		orderItem.setUser(user);
		orderItem.setUsername(user.getUserName());
		orderItem.setShippingAddress(shippingAddress);
		orderItem.setPayableAmount(product.getPrice() * quantity);
		product.setQuantity(product.getQuantity() - quantity);
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setProductName(product.getName());
		orderItem.setStatus(OrderStatus.ORDER_PLACED);
		order.getOrderList().add(orderItem);
		
		orderListRepo.save(orderItem);	
		inventoryRepo.save(product);
		userRepo.save(user);
		orderRepo.save(order);
		return orderItem;
	}
	
	public List<OrderList> updateOrderStatus(Long orderId){
		if(orderId == null) {
			return null;
		}
		Order order = orderRepo.findById(orderId).get();
		List<OrderList> orderItems = order.getOrderList();
		for(OrderList x : orderItems) {
			OrderStatus currentStatus = x.getStatus();
			OrderStatus updatedStatus = currentStatus.next();
			if(updatedStatus != OrderStatus.ORDER_CANCELLED) {
				x.setStatus(updatedStatus);
				orderListRepo.save(x);
			}	
		}
		return orderItems;
	}
}
