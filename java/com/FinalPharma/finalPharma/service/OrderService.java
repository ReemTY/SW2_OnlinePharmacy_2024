package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.Order;
//import com.FinalPharma.finalPharma.model.OrderDetails;
import com.FinalPharma.finalPharma.repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public List<Order> findAll() {
        try {
            return orderRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all orders: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all orders: " + ex.getMessage());
        }
    }

    public Order findById(Long orderId) {
        try {
            return orderRepository.findById(orderId).orElse(null);
        } catch (Exception ex) {
            logger.error("Error occurred while finding order with id {}: {}", orderId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding order with id " + orderId + ": " + ex.getMessage());
        }
    }

    public Order save(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception ex) {
            logger.error("Error occurred while saving order: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while saving order: " + ex.getMessage());
        }
    }

    public void delete(Order order) {
        try {
            orderRepository.delete(order);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting order: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting order: " + ex.getMessage());
        }
    }

//    public BigDecimal calculateTotalAmount(Order order) {
//        try {
//            BigDecimal totalAmount = BigDecimal.ZERO;
//            for (OrderDetails orderDetails : order.getOrderDetails()) {
//                totalAmount = totalAmount.add(orderDetails.getMedication().getPrice().multiply(BigDecimal.valueOf(orderDetails.getQuantity())));
//            }
//            return totalAmount;
//        } catch (Exception ex) {
//            logger.error("Error occurred while calculating total amount for order: {}", ex.getMessage(), ex);
//            throw new RuntimeException("Error occurred while calculating total amount for order: " + ex.getMessage());
//        }
//    }
}
