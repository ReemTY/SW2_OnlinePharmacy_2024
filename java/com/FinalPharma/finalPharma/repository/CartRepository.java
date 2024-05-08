package com.FinalPharma.finalPharma.repository;

import com.FinalPharma.finalPharma.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
