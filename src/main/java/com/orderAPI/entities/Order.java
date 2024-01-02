package com.orderAPI.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("order-list")
    private List<OrderList> orderList;
    
    private String username;

	public Order() {
		super();
		this.orderList = new ArrayList<>();
	}

	public Order(Long id, List<OrderList> orderList, String username) {
		super();
		this.id = id;
		this.orderList = new ArrayList<>();
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderList> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderList> orderList) {
		this.orderList = orderList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
