package com.orderAPI.DTO;

public class InventoryUpdateRequest {
	
	private Double price;
	private Integer quantity;
	private Double discountPercentage;
	private String festivalOfferName;
	
	public InventoryUpdateRequest() {
		super();
	}
	
	public InventoryUpdateRequest(Double price, Integer quantity, Double discountPercentage, String festivalOfferName) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.discountPercentage = discountPercentage;
		this.festivalOfferName = festivalOfferName;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
