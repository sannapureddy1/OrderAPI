package com.orderAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.DTO.InventoryUpdateRequest;
import com.orderAPI.DTO.ResponseDTO;
import com.orderAPI.entities.Discount;
import com.orderAPI.entities.Inventory;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.exceptions.DuplicateEntryException;
import com.orderAPI.exceptions.IllegalArgumentException;
import com.orderAPI.repositories.DiscountRepository;
import com.orderAPI.repositories.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {
	
	@Autowired
	private InventoryRepository InventoryRepo;
	
	@Autowired
	private DiscountRepository discountRepo;
	
	@Autowired
	private PricingService pricingService;
	
	
	/*
	  GET Methods - findAll() - no parameter required.
	              - findById() - pathVariable Long id must be passed.
	              - findByName() - pathVaribale String name must be passed.
	              - findByStatus() - requestParam String status must be provided (it must be either "Available" or "Sold Out".
	              - countByStatus() - requestParam String status must be provided (it must be either "Available" or "Sold Out".
	*/
	
	
	public List<Inventory> findAll(){
		if(InventoryRepo.findAll().isEmpty()) {
			throw new DataNotFoundException("There are no products present within the database, add some products and try again");
		}
		return InventoryRepo.findAll();
	}

	public Inventory findById(Long id) {
		if(! (InventoryRepo.existsById(id) )) {
			throw new DataNotFoundException("Product with given id not found");
		}
		return InventoryRepo.findById(id).get();
	}
	
	public Inventory findByName(String name) {
		if(! (InventoryRepo.existsByNameIgnoreCase(name) )) {
			throw new DataNotFoundException("Product with given name not found");
		}
		return InventoryRepo.findByNameIgnoreCase(name);
	}
	
    private boolean isValidStatus(String status) {
        return "Available".equalsIgnoreCase(status) || "Sold Out".equalsIgnoreCase(status);
    }	
    
	public List<Inventory> findByStatus(String status){
		if (!isValidStatus(status)) {
			throw new IllegalArgumentException("The given status is not valid, it must either be Available or Sold Out");
		}
		return InventoryRepo.findByStatusIgnoreCase(status);
	}
	
	public Long countByStatus(String status) {
		if (!isValidStatus(status)) {
			throw new IllegalArgumentException("The given status is not valid, it must either be Available or Sold Out");
		}
		return InventoryRepo.countByStatusIgnoreCase(status);
	}
	
	
	/*
	  POST Methods - add() - used to add product entities to the database.
	                       - requestBody Inventory temp i.e a request body of type Inventory must be provided. 
	               - addAll() - used to add a list of product entities at once to the database.
	                          - requestBody List<Inventory> temp i.e a request body that is a list of Inventory entities must be provided.
	*/
	
	
	public Inventory add(Inventory temp) {
		if(InventoryRepo.existsByNameIgnoreCase(temp.getName())) {
			throw new DuplicateEntryException("Product with given name already exists in the database");
		}
		Inventory newItem = new Inventory();
		if(temp.getName() == null || temp.getDescription() == null || temp.getPrice() == null || temp.getCategory() == null || temp.getQuantity() == null) {
			throw new IllegalArgumentException("Some of the name, description, price, category or quantity fields are empty");
		} 
		newItem.setName(temp.getName());
		newItem.setDescription(temp.getDescription());
		newItem.setPrice(temp.getPrice());
		newItem.setCategory(temp.getCategory());
		newItem.setQuantity(temp.getQuantity());
		Discount discount = new Discount(newItem, null, null);
		if(temp.getDiscount()!=null && (!(temp.getDiscount().getDiscountPercentage() != null && temp.getDiscount().getFestivalOfferName() != null))) {
			discount.setDiscountPercentage(temp.getDiscount().getDiscountPercentage() != null ? temp.getDiscount().getDiscountPercentage() : 0.0);
			discount.setFestivalOfferName(temp.getDiscount().getFestivalOfferName() != null ? temp.getDiscount().getFestivalOfferName() : "default");
		}
		if(temp.getQuantity() > 0) {
			newItem.setStatus("Available"); 
			discountRepo.save(discount);
		    newItem.setDiscount(discount);
		}
		else {
			newItem.setStatus("Sold Out");
			discountRepo.save(discount);
			newItem.setDiscount(discount);
		}
		pricingService.setPrice(newItem, newItem.getDiscount().getDiscountPercentage(), newItem.getDiscount().getFestivalOfferName());
		return InventoryRepo.save(newItem);
	}
	
	public List<Inventory> addAll(List<Inventory> temp){
		List<Inventory> productList = new ArrayList<>();
		for(Inventory x : temp) {
			if(InventoryRepo.existsByNameIgnoreCase(x.getName())) {
				throw new DuplicateEntryException("Product with the name " +x.getName()+ " already exists in the database");
			}
			if(x.getName() == null || x.getDescription() == null || x.getPrice() == null || x.getCategory() == null || x.getQuantity() == null) {
				throw new IllegalArgumentException("Some of the name, description, price, category or quantity fields are empty");
			}
			Inventory newItem = new Inventory(x.getName(), x.getDescription(), x.getPrice(), x.getCategory(), x.getQuantity(), null, null);
			Discount discount = new Discount(newItem, null, null);
			if(x.getDiscount()!=null && (x.getDiscount().getDiscountPercentage() == null || x.getDiscount().getFestivalOfferName() == null)) {
				discount.setDiscountPercentage(x.getDiscount().getDiscountPercentage() != null ? x.getDiscount().getDiscountPercentage() : 0.0);
				discount.setFestivalOfferName(x.getDiscount().getFestivalOfferName() != null ? x.getDiscount().getFestivalOfferName() : "default");
			}
			if(x.getQuantity() > 0) {
				newItem.setStatus("Available"); 
			}
			else {
				newItem.setStatus("Sold Out");
			}
			discountRepo.save(discount);
			newItem.setDiscount(discount);
			pricingService.setPrice(newItem, newItem.getDiscount().getDiscountPercentage(), newItem.getDiscount().getFestivalOfferName());
			productList.add(InventoryRepo.save(newItem));
		}
		return productList;
	}
	
	
	/*
	  PUT Methods - update() - pathVariable String name must be passed and a requestBody of type InventoryUpdateRequest is required which consists of Double price,
	                           Integer quantity, Double discountPercentage and String festivalOfferName based on fields that need to be updated.
	*/
	
	
	public Inventory update(String name, InventoryUpdateRequest temp) {
	    if (!InventoryRepo.existsByNameIgnoreCase(name)) {
			throw new DataNotFoundException("Product with given name not found");
	    }
	    Inventory updatedItem = InventoryRepo.findByNameIgnoreCase(name);
	    Discount currentDiscount = updatedItem.getDiscount();
	    if (temp.getPrice() != null) {
	    	updatedItem.setPrice(temp.getPrice());
//	        pricingService.resetPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
			pricingService.setPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	    }
	    if (temp.getQuantity() != null) {
	        updatedItem.setQuantity(temp.getQuantity());
	        updatedItem.setStatus(temp.getQuantity() > 0 ? "Available" : "Sold Out");
	        if (temp.getQuantity() == 0) {
	            pricingService.resetPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	            currentDiscount.setDiscountPercentage(0.0);
	            currentDiscount.setFestivalOfferName("default");
	        }
	    }
	    if (temp.getDiscountPercentage() != null && temp.getFestivalOfferName() != null) {
	    	throw new IllegalArgumentException("Provide either discountPercentage or festivalOfferName, not both");
	    }
	    if( (temp.getDiscountPercentage() == null || temp.getFestivalOfferName() == null) && updatedItem.getQuantity() > 0 ) {
	    	if(temp.getDiscountPercentage() != null) {
	    		pricingService.resetPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	    		currentDiscount.setDiscountPercentage(temp.getDiscountPercentage());
	    		currentDiscount.setFestivalOfferName("default");
	    		pricingService.setPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	    	}
	    	if(temp.getFestivalOfferName() != null) {
	    		pricingService.resetPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	    		currentDiscount.setDiscountPercentage(0.0);
	    		currentDiscount.setFestivalOfferName(temp.getFestivalOfferName());
	    		pricingService.setPrice(updatedItem, currentDiscount.getDiscountPercentage(), currentDiscount.getFestivalOfferName());
	    	}
	    }
	    InventoryRepo.save(updatedItem);
        return updatedItem;
	}
    
	
	/*
	  DELETE Methods - deleteById() - pathVariable Long id must be passed.
	                 - deleteByName() - pathVaribale String name must be passed.
	*/
	
	
	public String deleteById(Long id) {
		if(!InventoryRepo.existsById(id)) {
			throw new DataNotFoundException("Product with given id not found");
		}
		InventoryRepo.deleteById(id);
		return "Deleted";
	}
	
	public String deleteByName(String name) {
		if(!InventoryRepo.existsByNameIgnoreCase(name)) {
			throw new DataNotFoundException("Product with given name not found");
		}
		InventoryRepo.deleteByNameIgnoreCase(name);
		return "Deleted";
	}
		
	
	/*
	  POST Methods - sell() - a miscellaneous post method that was created to understand how sell function would be used without the user being created
	*/
	
	public ResponseDTO sell(String name, Integer soldQuantity) {
		if(! (InventoryRepo.existsByNameIgnoreCase(name) )) {
			return new ResponseDTO("The item does not exist",null);
		}
		Inventory temp = InventoryRepo.findByNameIgnoreCase(name);
		Integer existingQuantity = temp.getQuantity();
//		Long availableCount = countByStatus("Available");
		if (existingQuantity >= soldQuantity) {
			temp.setQuantity(existingQuantity - soldQuantity);
			if (temp.getQuantity() == 0) {
				temp.setStatus("Sold Out");
	          //  DiscountService.removeOffers(temp.getName());
//	            if (availableCount > 0) {
//	                availableCount = availableCount-1;
//	            }
			}
			InventoryRepo.save(temp);
			return new ResponseDTO(soldQuantity + " out of " + existingQuantity + " items have been sold", temp);
		}
		return new ResponseDTO("Insufficient quantity available",null);
	}

}
