package com.orderAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderAPI.entities.OrderList;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long>{

}
