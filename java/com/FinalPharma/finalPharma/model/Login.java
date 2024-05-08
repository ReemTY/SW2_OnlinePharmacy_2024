package com.FinalPharma.finalPharma.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Login {
    @NotBlank(message = "Username or email is required")
    @Email(message = "Invalid email format")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
