package com.orderAPI.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "Addresses" )
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference("address-user")
    private User user;
    
    @OneToMany(mappedBy = "shippingAddress", orphanRemoval = true)
    @JsonManagedReference("address-order")
    private List<OrderList> orderList;
	
	@Column(name = "Residence", nullable = false, length = 60)
    private String residence;

    @Column(name = "locality", length = 60)
    private String locality;
    
    @Column(name = "landmark", length = 60)
    private String landmark;

    @Column(name = "Country", nullable = false)
    private String country;
    
    @Column(name = "state", nullable = false)
    private String state;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "pincode", nullable = false, length = 6)
    private String pincode;

	public Address() {
		super();
	}
	
	public Address(User user, String residence, String locality, String landmark, String country, String state,
			String city, String pincode) {
		super();
		this.user = user;
		this.residence = residence;
		this.locality = locality;
		this.landmark = landmark;
		this.country = country;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public List<OrderList> getOrderList() {
//		return orderList;
//	}
//
//	public void setOrderList(List<OrderList> orderList) {
//		this.orderList = orderList;
//	}
	  
}
