package com.orderAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderAPI.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	public Boolean existsByUserId(Long userId);
	public Cart findByUserId(Long userId);
}
