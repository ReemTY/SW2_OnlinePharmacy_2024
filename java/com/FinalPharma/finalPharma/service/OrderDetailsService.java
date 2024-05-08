package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.OrderDetails;
import com.FinalPharma.finalPharma.repository.OrderDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepo orderDetailsRepository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepo orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsService.class);

    public List<OrderDetails> findAll() {
        try {
            return orderDetailsRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all order details: " + ex.getMessage());
        }
    }

    public OrderDetails findById(Integer orderDetailsId) {
        try {
            return orderDetailsRepository.findById(orderDetailsId).orElse(null);
        } catch (Exception ex) {
            logger.error("Error occurred while finding order details with id {}: {}", orderDetailsId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding order details with id " + orderDetailsId + ": " + ex.getMessage());
        }
    }

    public OrderDetails save(OrderDetails orderDetails) {
        try {
            return orderDetailsRepository.save(orderDetails);
        } catch (Exception ex) {
            logger.error("Error occurred while saving order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while saving order details: " + ex.getMessage());
        }
    }

    public void delete(OrderDetails orderDetails) {
        try {
            orderDetailsRepository.delete(orderDetails);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting order details: " + ex.getMessage());
        }
    }

    public Medication getProduct(OrderDetails orderDetails) {
        try {
            return orderDetails.getMedication();
        } catch (Exception ex) {
            logger.error("Error occurred while getting product from order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while getting product from order details: " + ex.getMessage());
        }
    }
}
