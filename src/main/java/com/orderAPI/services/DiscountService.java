package com.orderAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.entities.Discount;
import com.orderAPI.entities.Inventory;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.exceptions.IllegalArgumentException;
import com.orderAPI.repositories.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DiscountService {
	@Autowired
	private DiscountRepository discountRepo;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private PricingService pricingService;
	
	
	/*
	  PUT Methods - addOffers() - pathVariable String name must be passed.
	                            - either requestParam Double discountPercentage or requestParam festivalOfferName must be provided as well.
	              - removeOffers() - pathVariable String name must be passed.
	*/
	public Discount addOffers(String name, Double discountPercentage, String festivalOfferName) {
		if((discountPercentage != null && festivalOfferName != null) || (discountPercentage == null && festivalOfferName == null)) {
			throw new IllegalArgumentException("Either both the discount percentage and festival offer name have been provided or both the fields are null");
		}
		Inventory findProduct = inventoryService.findByName(name);
		if(findProduct != null) {
			Discount discount = discountRepo.findByProduct(findProduct);
			if(discount == null) {
				discount = new Discount(findProduct, (discountPercentage != null) ? discountPercentage : 0.0, (festivalOfferName != null) ? festivalOfferName : "default");
			}
			else {
				discount.setDiscountPercentage((discountPercentage != null) ? discountPercentage : 0.0);
				discount.setFestivalOfferName((festivalOfferName != null) ? festivalOfferName : "default");
			}
			pricingService.setPrice(findProduct, discount.getDiscountPercentage(), discount.getFestivalOfferName());
			return discountRepo.save(discount);
		}
		throw new DataNotFoundException("Product with given name was not found");
	}
	
	public Discount removeOffers(String name) {
		Inventory findProduct = inventoryService.findByName(name);
		if(findProduct != null) {
			Discount discount = discountRepo.findByProduct(findProduct);
			if(discount == null) {
				discount = new Discount(findProduct, 0.0, "default");
			}
			else {
				pricingService.resetPrice(findProduct, discount.getDiscountPercentage(), discount.getFestivalOfferName());
				discount.setDiscountPercentage(0.0);
				discount.setFestivalOfferName("default");
			}
			return discountRepo.save(discount);
		}
		throw new DataNotFoundException("Product with given name was not found");
	}
	
	
	/*
	  DELETE Methods - deleteDiscount() - pathVariable String name must be passed.
	                                    - it helps to delete the discount for a given product without deleting the product.
	*/
	
	
    public String deleteDiscount(String name) {
    	Inventory existingProduct = inventoryService.findByName(name);
    	if( existingProduct != null && existingProduct.getDiscount() != null)  {
    		Discount discount = existingProduct.getDiscount();
    		pricingService.resetPrice(existingProduct, discount.getDiscountPercentage(), discount.getFestivalOfferName());
    		existingProduct.setDiscount(null);
    		discountRepo.delete(discount);
    		return "Deleted";
    	}
    	throw new DataNotFoundException("Discounts on the product with the given name was not found");
    }
    
    
    
//  public void updateOffer(Inventory product) {
//  Discount discount = discountRepo.findByProduct(product);
//  if (discount != null) {
//  	if ("Sold Out".equalsIgnoreCase(product.getStatus())) {
//          removeOffers(product.getName());
//      } 
//  	else {
//  		addOffers(product.getName(), discount.getDiscountPercentage(), discount.getFestivalOfferName());
//  		discountRepo.save(discount);
//  	}
//  }
//}
   
    
}
