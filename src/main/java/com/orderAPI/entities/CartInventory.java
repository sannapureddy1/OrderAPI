package com.orderAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_products")
public class CartInventory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference("cart-products")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-products")
    private Inventory inventory;
    
	@Column(name = "product_name")
	private String productName;

	@Column(name = "quantity")
    private Integer soldQuantity;

	@Column(name = "unit_price")
	private Double unitPrice;
	
    @Column(name = "total_unit_price")
    private Double totalUnitPrice;

	public CartInventory() {
		super();
	}

//	public CartInventory(Cart cart, Inventory inventory, Integer soldQuantity, Double unitPrice,
//			Double totalUnitPrice) {
//		super();
//		this.cart = cart;
//		this.inventory = inventory;
//		this.soldQuantity = soldQuantity;
//		this.unitPrice = unitPrice;
//		this.totalUnitPrice = totalUnitPrice;
//	}

	public CartInventory(Cart cart, Inventory inventory, String productName, Integer soldQuantity,
			Double unitPrice, Double totalUnitPrice) {
		super();
		this.cart = cart;
		this.inventory = inventory;
		this.productName = productName;
		this.soldQuantity = soldQuantity;
		this.unitPrice = unitPrice;
		this.totalUnitPrice = totalUnitPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getProductName() {
		return productName;
	}

	public Integer getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getTotalUnitPrice() {
		return totalUnitPrice;
	}

	public void setTotalUnitPrice(Double totalUnitPrice) {
		this.totalUnitPrice = totalUnitPrice;
	}
    
//	public Double getTotalPrice() {
//        return soldQuantity * totalUnitPrice;
//    }
    
}
