package com.FinalPharma.finalPharma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OrderDetails")
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "medication_id", referencedColumnName = "medicationId")
    private Medication medication;

    @Column(nullable = false)
    private Integer quantity;

    // getters and setters
}
