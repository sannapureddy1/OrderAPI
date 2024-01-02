package com.orderAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderAPI.entities.Discount;
import com.orderAPI.entities.Inventory;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>{
	Discount findByProduct(Inventory product);
//	Boolean existsByNameIgnoreCase(String name);
//
//	void deleteByNameIgnoreCase(String name);
}
