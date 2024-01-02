package com.orderAPI.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.orderAPI.enumerations.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_list")
public class OrderList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
    @JsonBackReference("order-list")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    @JsonBackReference("user-order")
	private User user;
	
	private String username; 
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	@JsonBackReference("address-order")
	private Address shippingAddress;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference("inventory-orders")
	private Inventory product;
	
	@Column(name = "product_name")
	private String productName;

	@Column(name = "order_date", nullable = false)
	@CreationTimestamp
	private LocalDateTime orderDate;
	
	@Column(name = "last_updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;

	@Column(name = "payable_amount", nullable = false)
	private Double payableAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;
	
	public OrderList() {
		super();
	}

//	public OrderList(Order order, User user, String username, Address shippingAddress, Inventory product,
//			Double payableAmount, OrderStatus status) {
//		super();
//		this.order = order;
//		this.user = user;
//		this.username = username;
//		this.shippingAddress = shippingAddress;
//		this.product = product;
//		this.payableAmount = payableAmount;
//		this.status = status;
//	}
//	
//    public OrderList(Long id, String username, LocalDateTime orderDate, LocalDateTime lastUpdatedDate, Double payableAmount, OrderStatus status) {
//    	super();
//        this.id = id;
//        this.username = username;
//        this.orderDate = orderDate;
//        this.lastUpdatedDate = lastUpdatedDate;
//        this.payableAmount = payableAmount;
//        this.status = status;
//    }
	
	public OrderList(Order order, User user, String username, Address shippingAddress, Inventory product,
			String productName, Double payableAmount, OrderStatus status) {
		super();
		this.order = order;
		this.user = user;
		this.username = username;
		this.shippingAddress = shippingAddress;
		this.product = product;
		this.productName = productName;
		this.payableAmount = payableAmount;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Inventory getProduct() {
		return product;
	}

	public void setProduct(Inventory product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
