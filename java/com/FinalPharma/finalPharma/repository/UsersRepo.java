package com.FinalPharma.finalPharma.repository;

import com.FinalPharma.finalPharma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
    boolean existsByUsername(String username);



}
