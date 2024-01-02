package com.orderAPI.repositories;

//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import com.orderAPI.entities.OrderList;
import com.orderAPI.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByUserNameIgnoreCase(String username);
	User getByUserNameIgnoreCase(String username);
	
	void deleteByUserNameIgnoreCase(String username);
	
//	@Query(value = "SELECT * FROM ORDER_LIST WHERE ORDER_LIST.username = ?1 and ORDER_LIST.status = 'ORDER_CLOSED' ", nativeQuery = true)
//	public List<OrderList> orderHistory(String username);
}
