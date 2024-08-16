package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	Page<OrderDetail> findByOrderDetailIdContaining(int OrderDetailId,Pageable pageable);
	@Query("SELECT d FROM OrderDetail d WHERE d.order.orderId =?1")	
	List<OrderDetail> findByOrderId(Long id);
}
