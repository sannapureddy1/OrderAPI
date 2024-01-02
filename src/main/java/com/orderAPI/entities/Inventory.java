package com.orderAPI.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Price")
	private Double price;
	
	@Column(name = "Category")
	private String category;
	
	@Column(name = "Quantity")
	private Integer quantity;
	
	@Column(name = "Status")
	private String status;
	
//	@ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
//	@JsonBackReference
//  private List<Cart> carts;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JsonBackReference
//	private Cart cart;
	
	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
	@JsonManagedReference("inventory-products")
    private List<CartInventory> productList;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonManagedReference("inventory-discount")
	private Discount discount;
	
	@OneToMany(mappedBy = "product")
	@JsonManagedReference("inventory-orders")
    private List<OrderList> orderList;
	
	public Inventory() {
		super();
	}
	
	public Inventory(String name, String description, Double price, String category, int quantity, String status) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.quantity = quantity;
		this.status = status;
	}
	
	public Inventory(String name, String description, Double price, String category, Integer quantity, String status, Discount discount) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.quantity = quantity;
		this.status = status;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public List<CartInventory> getProductList() {
		return productList;
	}

	public void setProductList(List<CartInventory> productList) {
		this.productList = productList;
	}

//	public Cart getCart() {
//		return cart;
//	}
//
//	public void setCart(Cart cart) {
//		this.cart = cart;
//	}

//	public List<Cart> getCarts() {
//		return carts;
//	}
//
//	public void setCarts(List<Cart> carts) {
//		this.carts = carts;
//	}

}
