package com.orderAPI.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
//import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("cart-user")
    private User user;
    
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//        name = "cart_inventory",
//        joinColumns = @JoinColumn(name = "cart_id"),
//        inverseJoinColumns = @JoinColumn(name = "inventory_id")
//    )
//    @JsonManagedReference
//    private List<Inventory> productList;
    
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Inventory> productList;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cart-products")
    private List<CartInventory> productList;
    
    private Double total;

	public Cart() {
        super();
		this.productList = new ArrayList<>();
		calculateTotal();
    }

	public Cart(Long id, User user) {
		super();
		this.id = id;
		this.user = user;
		this.productList = new ArrayList<>();
		calculateTotal();
	}

	public Long getId() {
		return id;
	}

	public void setCartId(Long id) {
		this.id = id;
	}
	
    public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartInventory> getProductList() {
		return productList;
	}

	public void setProductList(List<CartInventory> productList) {
		this.productList = productList;
	}

    public void calculateTotal() {
        if (productList != null && !productList.isEmpty()) {
            total = productList.stream()
                    .mapToDouble(CartInventory::getTotalUnitPrice)
                    .sum();
        } else {
            total = 0.0;
        }
        
    }
    
//	public List<Inventory> getProductList() {
//		return productList;
//	}
//
//	public void setProductList(List<Inventory> productList) {
//		this.productList = productList;
//	}

}
