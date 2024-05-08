package com.FinalPharma.finalPharma.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Carts")
@Data

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    
    @Transient
    public Integer getTotalQuantity() {
        return cartItems.stream()
               .mapToInt(CartItem::getQuantity)
               .sum();
    }

    @Transient
    public BigDecimal getTotalPrice() {
        return cartItems.stream()
               .map(ci -> ci.getMedication().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // getters and setters
}