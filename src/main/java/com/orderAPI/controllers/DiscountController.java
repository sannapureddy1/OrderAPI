package com.orderAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderAPI.entities.Discount;
import com.orderAPI.services.DiscountService;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
	@Autowired
	private DiscountService discountService;
	
    /*
      PathVariable - String {name}
      RequestParam - Either Double discountPercentage or String festivalOfferName but not both
      RequestURL - http://localhost:9009/discounts/add/{name}?discountPercentage={value} (or)
                 - http://localhost:9009/discounts/add/{name}?festivalOfferName={value}
    */
	@PutMapping("/add/{name}")
	public Discount addOffers(@PathVariable String name, @RequestParam(required = false) Double discountPercentage, @RequestParam(required = false) String festivalOfferName) {
		return discountService.addOffers(name, discountPercentage, festivalOfferName);
	}
	
    /*
      PathVariable - String {name}
      RequestURL - http://localhost:9009/discounts/remove/{name} 
    */
	@PutMapping("/remove/{name}")
	public Discount removeOffers(@PathVariable String name) {
		return discountService.removeOffers(name);
	}
	
	
    /*
      PathVariable - String {name}
      RequestURL - http://localhost:9009/discounts/delete/{name}
    */
	@DeleteMapping("/delete/{name}")
	public String deleteDiscount(@PathVariable String name) {
		return discountService.deleteDiscount(name);
	}
	
}
