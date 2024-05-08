package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Order;
import com.FinalPharma.finalPharma.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all orders: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching order with id {}: {}", orderId, ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/addOrder")
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        try {
//            order.setTotalAmount(orderService.calculateTotalAmount(order));
//            Order newOrder = orderService.save(order);
//            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
//        } catch (Exception ex) {
//            logger.error("Error occurred while creating order: {}", ex.getMessage(), ex);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PutMapping("/{orderId}")
//    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody Order order) {
//        try {
//            Order existingOrder = orderService.findById(orderId);
//            if (existingOrder == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            order.setOrderId(orderId);
//            order.setTotalAmount(orderService.calculateTotalAmount(order));
//            Order updatedOrder = orderService.save(order);
//            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
//        } catch (Exception ex) {
//            logger.error("Error occurred while updating order with id {}: {}", orderId, ex.getMessage(), ex);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            orderService.delete(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting order with id {}: {}", orderId, ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
