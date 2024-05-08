package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Cart;
import com.FinalPharma.finalPharma.model.CartItem;
import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.Order;
import com.FinalPharma.finalPharma.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping ("/createCart")
    public ResponseEntity<Cart> createCart() {
        Cart cart = cartService.createCart();
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Integer cartId, @RequestBody Medication medication) {
        Cart cart = cartService.getCartById(cartId);
        cart = cartService.addItemToCart(cart, medication);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Integer cartId, @PathVariable Integer cartItemId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem cartItem = cart.getCartItems().stream()
               .filter(ci -> ci.getCartItemId().equals(cartItemId))
               .findFirst()
               .orElseThrow();
        cart = cartService.removeItemFromCart(cart, cartItem);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer cartId) {
        Cart cart = cartService.getCartById(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    
    @GetMapping("/{cartId}/total-quantity")
    public ResponseEntity<Integer> getTotalQuantity(@PathVariable Integer cartId) {
        Cart cart = cartService.getCartById(cartId);
        Integer totalQuantity = cartService.getTotalQuantity(cart);
        return new ResponseEntity<>(totalQuantity, HttpStatus.OK);
    }

    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Integer cartId) {
        Cart cart = cartService.getCartById(cartId);
        BigDecimal totalPrice = cartService.getTotalPrice(cart);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @PostMapping("/confirmOrder")
    public void confirmOrder(@RequestBody Order order,Cart Cart) {
        cartService.confirmOrder(order, Cart);
    }
}