package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.User;
import com.FinalPharma.finalPharma.repository.UsersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UsersRepo userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UsersRepo userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception ex) {
            logger.error("Error occurred while finding user by username '{}': {}", username, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding user by username '" + username + "': " + ex.getMessage());
        }
    }

    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            logger.error("Error occurred while finding user by email '{}': {}", email, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding user by email '" + email + "': " + ex.getMessage());
        }
    }
//    @Secured("ROLE_ADMIN")
    public void deleteUser(Integer user_id) {
        try {
            User userToDelete = userRepository.findById(user_id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

            userRepository.delete(userToDelete);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting user with id '{}': {}", user_id, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting user with id '" + user_id + "': " + ex.getMessage());
        }
    }
    private void validateSignUp(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
    }
    public User saveUser(User user) {

        validateSignUp(user);

        // Remove the creation of a new User object

        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setAddress(user.getAddress());
//        newUser.setUserRole(UserRole.ROLE_USER);

        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all users: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all users: " + ex.getMessage());
        }
    }
    public User updateUser(Integer user_id, User updatedUser) {
        try {
            User existingUser = userRepository.findById(user_id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setName(updatedUser.getName());
            existingUser.setAddress(updatedUser.getAddress());
//            existingUser.setUserRole(updatedUser.getUserRole());

            return userRepository.save(existingUser);
        } catch (Exception ex) {
            logger.error("Error occurred while updating user with id '{}': {}", user_id, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while updating user with id '" + user_id + "': " + ex.getMessage());
        }
    }    
//    public User authenticate(String usernameOrEmail, String password) {
//        User userByUsername = findByUsername(usernameOrEmail);
//        if (userByUsername != null && passwordEncoder().matches(password, userByUsername.getPassword())) {
//            return userByUsername;
//        }
//        User userByEmail = findByEmail(usernameOrEmail);
//        if (userByEmail != null && passwordEncoder().matches(password, userByEmail.getPassword())) {
//            return userByEmail;
//        }
//        return null;
    }

//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
