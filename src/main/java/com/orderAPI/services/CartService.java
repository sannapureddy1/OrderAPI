package com.orderAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderAPI.entities.Cart;
import com.orderAPI.entities.CartInventory;
import com.orderAPI.entities.Inventory;
import com.orderAPI.entities.User;
import com.orderAPI.exceptions.DataNotFoundException;
import com.orderAPI.repositories.CartRepository;
import com.orderAPI.repositories.InventoryRepository;
import com.orderAPI.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private InventoryRepository inventoryRepo;
	
    public Cart createCart(Long userId) {
    	if(userRepo.existsById(userId)) {
    		User user = userRepo.findById(userId).get();
    		if(user.getCart() != null) {
    			return user.getCart();
    		}
    		Cart newCart = new Cart();
    		newCart.setUser(user);
    		cartRepo.save(newCart);
    		user.setCart(newCart);
    		userRepo.save(user);
    		return newCart;
    	}
    	return null;
    }
    
	/*
	  PUT Methods - addToCart() - pathVariable Long userId must be passed.
	                            - RequestParams of type String productName and Integer soldQuantity are required to implement general use-case. 
	              - removeFromCart() - pathVariable Long userId must be passed.
	                                 - RequestParam of type String productName is required to remove specific product from the cart.
	              - removeAllFromCart() - pathVariable Long userId must be passed to remove all products from cart for a given user.
	*/
    
    public Cart addToCart(Long userId, String productName, Integer soldQuantity) {
    	if(userId == null || productName == null) {
    		throw new IllegalArgumentException("Either userId or productName field is empty");
    	}
    	if(!cartRepo.existsByUserId(userId) || !inventoryRepo.existsByNameIgnoreCase(productName)) {
        	throw new DataNotFoundException("Either the user or the product with given values are not found");
    	}
		//Cart existingCart = cartRepo.findById(cartId).get();
		Cart existingCart = cartRepo.findByUserId(userId);
		Inventory product = inventoryRepo.findByNameIgnoreCase(productName);
		if(product.getQuantity() >= soldQuantity) {
			CartInventory existingProduct = getExistingProductInCart(existingCart, productName);
			if (existingProduct == null) {
				//soldQuantity != null ? soldQuantity : 1
				existingProduct = new CartInventory(existingCart, product, product.getName(), soldQuantity, product.getPrice() , soldQuantity != null ? product.getPrice() * soldQuantity : product.getPrice());
				product.setQuantity(product.getQuantity() - soldQuantity);
				product.getProductList().add(existingProduct);
				inventoryRepo.save(product);
				existingCart.getProductList().add(existingProduct);
				
			}
			else {
				product.setQuantity(product.getQuantity() - soldQuantity);
				existingProduct.setSoldQuantity(existingProduct.getSoldQuantity() + soldQuantity);
				existingProduct.setTotalUnitPrice(existingProduct.getSoldQuantity() * product.getPrice());
				inventoryRepo.save(existingProduct.getInventory());
			}
		}
		existingCart.calculateTotal();
		return cartRepo.save(existingCart);
    }
    
    public Cart removeFromCart(Long userId, String productName) {
    	if(userId == null || productName == null) {
    		throw new IllegalArgumentException("Either userId or productName field is empty");
    	}
    	if( !cartRepo.existsByUserId(userId) ) {
    		throw new DataNotFoundException("User with the given id not found");
    	}
		Cart existingCart = cartRepo.findByUserId(userId);
		existingCart.getProductList().remove( getExistingProductInCart(existingCart, productName) );
		existingCart.calculateTotal();
		return cartRepo.save(existingCart);
    }
    
    public Cart removeAllFromCart(Long userId) {
    	if( !cartRepo.existsByUserId(userId)) {
    		throw new DataNotFoundException("User with the given id not found");
    	}
    	Cart existingCart = cartRepo.findByUserId(userId);
		List<CartInventory> productList = existingCart.getProductList();
		existingCart.getProductList().removeAll(productList);
		existingCart.calculateTotal();
		return cartRepo.save(existingCart);
//	    if (existingCart != null) {
//            existingCart.getProductList().clear();
//            return cartRepo.save(existingCart);
//        }
    }
    
//    private boolean isProductAlreadyInCart(Cart cart, String productName) {
//        for (Inventory cartProduct : cart.getProductList()) {
//            if (cartProduct.getName().equalsIgnoreCase(productName)) {
//                return true;
//            }
//        }
//        return false;
//    }
    
    private CartInventory getExistingProductInCart(Cart cart, String productName) {
        for (CartInventory cartProduct : cart.getProductList()) {
            if (cartProduct.getInventory().getName().equalsIgnoreCase(productName)) {
                return cartProduct;
            }
        }
        return null;
    }
}
