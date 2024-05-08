package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.Cart;
import com.FinalPharma.finalPharma.model.CartItem;
import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.Order;
import com.FinalPharma.finalPharma.repository.CartRepository;
import com.FinalPharma.finalPharma.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final OrderRepo orderRepository;

    @Autowired
    public CartService(CartRepository cartRepository, OrderRepo orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository; // Initialize OrderRepo
    }

    public Cart createCart() {
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Cart cart, Medication medication) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMedication(medication);
//        cartItem.setQuantity(quantity);
        cart.getCartItems().add(cartItem);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Cart cart, CartItem cartItem) {
        cart.getCartItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElseThrow();
    }

    
    public Integer getTotalQuantity(Cart cart) {
        return cart.getTotalQuantity();
    }

    public BigDecimal getTotalPrice(Cart cart) {
        return cart.getTotalPrice();
    }

    public void confirmOrder(Order order, Cart Cart) {
        // Here you can implement the logic to finalize the order,
        // such as creating an order record in the database.
        order.setOrderDate(LocalDateTime.now());
        BigDecimal totalAmount = BigDecimal.valueOf(getTotalQuantity(Cart));
        order.setTotalAmount(totalAmount);
        // Set order details like customer, items, total price, etc.
        orderRepository.save(order); // Use orderRepository to save the order
    }
}