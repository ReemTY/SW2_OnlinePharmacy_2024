
package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.SignUp;
import com.FinalPharma.finalPharma.model.User;
import com.FinalPharma.finalPharma.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignUpController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp signUp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : errors) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMap);
        }
        try {
            User newUser = new User();
            newUser.setUsername(signUp.getUsername());
            newUser.setPassword(signUp.getPassword());
            newUser.setEmail(signUp.getEmail());
            newUser.setName(signUp.getName());
            newUser.setAddress(signUp.getAddress());

            User savedUser = userService.saveUser(newUser);

            // Dummy response for demonstration
            String message = "Sign-up successful for user: " + savedUser.getUsername();
            return ResponseEntity.ok().body(message);
        } catch (RuntimeException ex) {
            logger.error("Error occurred during sign-up: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}