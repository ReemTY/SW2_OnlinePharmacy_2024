package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.OrderDetails;
import com.FinalPharma.finalPharma.service.OrderDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    @GetMapping("/getAll")
    public List<OrderDetails> getAllOrderDetails() {
        try {
            return orderDetailsService.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all order details: " + ex.getMessage());
        }
    }

    @GetMapping("/{orderDetailsId}")
    public OrderDetails getOrderDetailsById(@PathVariable Integer orderDetailsId) {
        try {
            return orderDetailsService.findById(orderDetailsId);
        } catch (Exception ex) {
            logger.error("Error occurred while finding order details with id {}: {}", orderDetailsId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding order details with id " + orderDetailsId + ": " + ex.getMessage());
        }
    }

    @PostMapping("/addOrderDetails")
    public OrderDetails addOrderDetails(@RequestBody OrderDetails orderDetails) {
        try {
            return orderDetailsService.save(orderDetails);
        } catch (Exception ex) {
            logger.error("Error occurred while adding order details: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while adding order details: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{orderDetailsId}")
    public void deleteOrderDetails(@PathVariable Integer orderDetailsId) {
        try {
            OrderDetails orderDetails = orderDetailsService.findById(orderDetailsId);
            orderDetailsService.delete(orderDetails);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting order details with id {}: {}", orderDetailsId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting order details with id " + orderDetailsId + ": " + ex.getMessage());
        }
    }

    @GetMapping("/product/{orderDetailsId}")
    public Medication getProduct(@PathVariable Integer orderDetailsId) {
        try {
            OrderDetails orderDetails = orderDetailsService.findById(orderDetailsId);
            return orderDetailsService.getProduct(orderDetails);
        } catch (Exception ex) {
            logger.error("Error occurred while getting product for order details with id {}: {}", orderDetailsId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while getting product for order details with id " + orderDetailsId + ": " + ex.getMessage());
        }
    }
}
