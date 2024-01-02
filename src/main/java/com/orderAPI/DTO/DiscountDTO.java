package com.orderAPI.DTO;

public class DiscountDTO {
	private Double price;
	private Double discountPrice;
	private Double festivalOfferPrice;
	
	public DiscountDTO() {
		super();
	}
	public DiscountDTO(Double price, Double discountPrice, Double festivalOfferPrice) {
		super();
		this.price = price;
		this.discountPrice = discountPrice;
		this.festivalOfferPrice = festivalOfferPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public Double getFestivalOfferPrice() {
		return festivalOfferPrice;
	}
	public void setFestivalOfferPrice(Double festivalOfferPrice) {
		this.festivalOfferPrice = festivalOfferPrice;
	}
	
	
}
