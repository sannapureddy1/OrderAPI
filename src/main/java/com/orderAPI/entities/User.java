package com.orderAPI.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(unique = true, nullable = false)
	private Long phoneNumber;
	
	@Column(unique = true, nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("cart-user")
	private Cart cart;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)//cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})// , orphanRemoval = true)
    @JsonManagedReference("address-user")
    private List<Address> addressList;
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-order")
    private List<OrderList> orderList;
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, Long phoneNumber, String userName, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
	}

	public User(Long id, String firstName, String lastName, String email, Long phoneNumber, String userName,
			String password, Cart cart, List<Address> addressList) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.cart = cart;
		this.addressList = addressList;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	
	public void addToAddressList(Address address) {
		addressList.add(address);
	}
	
	public void removeFromAddressList(Address address) {
		addressList.remove(address);
	}
	
	public List<OrderList> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderList> orderList) {
		this.orderList = orderList;
	}
	
}
 