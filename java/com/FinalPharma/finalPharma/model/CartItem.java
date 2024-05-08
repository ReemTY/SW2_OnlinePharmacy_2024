package com.FinalPharma.finalPharma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cart_Items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "medication_id", referencedColumnName = "medicationId")
    private Medication medication;

    private Integer quantity;


    // getters and setters
}
