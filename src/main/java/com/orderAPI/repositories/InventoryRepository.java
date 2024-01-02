package com.orderAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderAPI.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
	
    List<Inventory> findByStatusIgnoreCase(String status);
	Long countByStatusIgnoreCase(String status);
	
	Inventory findByNameIgnoreCase(String name);
	Boolean existsByNameIgnoreCase(String name);
	void deleteByNameIgnoreCase(String name);
	
}
