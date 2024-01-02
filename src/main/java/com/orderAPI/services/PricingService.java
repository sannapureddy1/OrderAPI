package com.orderAPI.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.orderAPI.entities.Inventory;

@Service
public class PricingService {

	/*
	  Values that are valid for the field "festivalOfferName" in Discount entity are provided in the given enum with corresponding discountPercentage values
	*/
	enum FestivalOffer{
		BLACK_FRIDAY_SALE(50),
		CHRISTMAS_SALE(25),
		THANKSGIVING_SALE(10),
		DEFAULT(0);
		
		private int discountPercentage;
		
		FestivalOffer(int discountPercentage) {
		    this.discountPercentage = discountPercentage;
		}
		
		public static int getDiscountPercentage(String festivalOfferName) {
			FestivalOffer offer = valueOf(festivalOfferName.toUpperCase().replace(" ", "_"));
			return offer.discountPercentage;
	    }
	
	}

	public void setPrice(Inventory product, Double discountPercentage, String festivalOfferName) {
		Double price = product.getPrice();
		if(discountPercentage != null) {
			//discount price = (price * (discountPercentage / 100)
		    price = price -  (price * (discountPercentage / 100.0));   
		}
		if(festivalOfferName != null) {
			Integer festivalOfferPercentage = FestivalOffer.getDiscountPercentage(festivalOfferName);
			price = price - (price * (festivalOfferPercentage / 100.0));
		}
		product.setPrice(roundPrice(price));
	}

	public void resetPrice(Inventory product, Double discountPercentage, String festivalOfferName) {
		Double price = product.getPrice();
		if(discountPercentage != null) {
			price = price / (1-(discountPercentage/100.0));
		}
		if(festivalOfferName != null) {
			Integer festivalOfferPercentage = FestivalOffer.getDiscountPercentage(festivalOfferName);
			price = price / (1-(festivalOfferPercentage/100.0));
		}
		product.setPrice(roundPrice(price));
	}

    private Double roundPrice(Double price) {
        BigDecimal roundedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
        return roundedPrice.doubleValue();
    }
}
