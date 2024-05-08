package com.FinalPharma.finalPharma.repository;

import com.FinalPharma.finalPharma.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByOrderId (Long orderId);
}
