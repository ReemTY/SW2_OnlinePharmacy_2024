package com.FinalPharma.finalPharma.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUp {
    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 40, message = "Name must be between 4 and 40 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Size(max = 40, message = "Email must not exceed 40 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,20}$",
            message = "Password must be between 6 and 20 characters long and contain at least one letter and one number")
    private String password;

    
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

//    @Enumerated(EnumType.STRING)
//    @NotNull(message = "User role is required")
//    @Column(name = "UserRole")
//    private UserRole userRole;


}
