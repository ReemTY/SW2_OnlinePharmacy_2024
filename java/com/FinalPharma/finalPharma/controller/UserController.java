package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.User;
import com.FinalPharma.finalPharma.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            return ResponseEntity.ok().body(user);
        } catch (RuntimeException ex) {
            logger.error("Error occurred while finding user by username {}: {}", username, ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        try {
            User user = userService.findByEmail(email);
            return ResponseEntity.ok().body(user);
        } catch (RuntimeException ex) {
            logger.error("Error occurred while finding user by email {}: {}", email, ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok().body(users);
        } catch (RuntimeException ex) {
            logger.error("Error occurred while fetching all users: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer user_id, @RequestBody User updatedUser) {
        try {
            User updatedUserData = userService.updateUser(user_id, updatedUser);
            return ResponseEntity.ok().body(updatedUserData);
        } catch (RuntimeException ex) {
            logger.error("Error occurred while updating user with id {}: {}", user_id, ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer user_id) {
        try {
            userService.deleteUser(user_id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            logger.error("Error occurred while deleting user with id {}: {}", user_id, ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        }
    }
}
