package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Login;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api")
public class LoginController {

    //    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//    @Autowired
//    public LoginController(UserService userService) {
//        this.userService = userService;
//    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/admin")
//    public String admin() {
//        return "This is the admin page";
//    }
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/user")
//    public String user() {
//        return "This is the admin page";
//    }

    @PostMapping("/login") // Corrected endpoint path to "/login"
    public ResponseEntity<?> login(@Valid @RequestBody Login login, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : errors) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMap);
        }
        try {
            // Call the UserService to authenticate the user
            // (implementation depends on your authentication mechanism)
            // For example:
//            User authenticatedUser = userService.authenticate(login.getUsernameOrEmail(), login.getPassword());

            // Dummy response for demonstration
            String message = "Login successful for user: " + login.getUsernameOrEmail();
            return ResponseEntity.ok().body(message);
        } catch (RuntimeException ex) {
            logger.error("Error occurred during login: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
