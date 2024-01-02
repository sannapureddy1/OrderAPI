package com.orderAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Discount")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
    @JoinColumn(name = "product_id")
	@JsonBackReference("inventory-discount")
	private Inventory product;
	
	private Double discountPercentage;
	
	private String festivalOfferName;
	
	public Discount() {
		super();
	}
	
	public Discount(Inventory product, Double discountPercentage, String festivalOfferName) {
		super();
		this.product = product;
		this.discountPercentage = discountPercentage;
		this.festivalOfferName = festivalOfferName;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Inventory getProduct() {
		return product;
	}
	
	public void setProduct(Inventory product) {
		this.product = product;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}
	
	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getFestivalOfferName() {
		return festivalOfferName;
	}
	
	public void setFestivalOfferName(String festivalOfferName) {
		this.festivalOfferName = festivalOfferName;
	}
	
}
