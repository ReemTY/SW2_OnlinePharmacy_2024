package com.FinalPharma.finalPharma.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotNull(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Column(name = "Username", unique = true)
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$", message = "Password must contain letters and numbers")
    @Column(name = "Password")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    @Column(name = "Email")
    private String email;

    @NotNull(message = "Full Name is required")
    @Size(max = 100)
    @Column(name = "Name")
    private String name;

    
    @Size(max = 255)
    @Column(name = "Address")
    private String address;

//    @Enumerated(EnumType.STRING)
//    @NotNull(message = "User role is required")
//    @Column(name = "UserRole")
//    private UserRole userRole;


    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
