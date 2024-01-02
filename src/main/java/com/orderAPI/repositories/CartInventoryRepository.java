package com.orderAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderAPI.entities.CartInventory;

@Repository
public interface CartInventoryRepository extends JpaRepository<CartInventory, Long>{

}
