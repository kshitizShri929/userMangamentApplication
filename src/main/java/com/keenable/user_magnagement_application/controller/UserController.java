
//             return ResponseEntity.ok(new ApiResponse("Username retrieved successfully.", decryptedUsername));
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body(new ApiResponse("Username not found.", null));
//         }
//     } catch (Exception e) {
//         e.printStackTrace(); // Log exception details
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(new ApiResponse("Invalid request or decryption failed.", null));
//     }
// }
// }
package com.keenable.user_magnagement_application.controller;

import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.service.UserService;
//import com.keenable.user_magnagement_application.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register/{username}")
    public ResponseEntity<ApiResponse> createUser(@PathVariable String username, @RequestBody UserEntity userData) {
        String usernamePattern = "^[a-zA-Z0-9]+$";

        if (!username.matches(usernamePattern)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid username format. Only alphabetic, numeric, or alphanumeric characters are allowed.", null));
        }

        if (!username.equals(userData.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Username in the URL and request body must match.", null));
        }

        if (userService.doesUserNameExist(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Username already exists.", null));
        }

        userData.setUserName(username);
        UserEntity createdUser = userService.createUser(userData);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User registered successfully.", createdUser));
    }

    // Retrieve all users
    @GetMapping("/retrieve")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse("No users found.", null));
        }
        return ResponseEntity.ok(new ApiResponse("Users retrieved successfully.", users));
    }

    // Retrieve a specific user by username
    @GetMapping("/retrieve/{username}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable String username) {
        try {
            UserEntity user = userService.getUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse("User retrieved successfully.", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found.", null));
        }
    }

    // Delete all users
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("All users deleted successfully.", null));
    }

    // Delete a specific user by username
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username) {
        boolean isDeleted = userService.deleteUserByUsername(username);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse("User deleted successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found.", null));
        }
    }

    // Update user details
    @PutMapping("/update/{username}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable String username,
            @RequestBody UserEntity userData) {
        try {
            UserEntity updatedUser = userService.updateUser(username, userData);
            return ResponseEntity.ok(new ApiResponse("User updated successfully.", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found.", null));
        }
    }

    // Retrieve user's username using encrypted password
    @GetMapping("/retrieve/password/{encryptedPassword}")
    public ResponseEntity<ApiResponse> retrieveUsername(@PathVariable String encryptedPassword) {
        try {
            // Decrypt the password to obtain the username
            String userName = userService.decryptPassword(encryptedPassword);
            UserEntity user = userService.getUserByUsername(userName);

            if (user != null) {
                return ResponseEntity.ok(new ApiResponse("Username retrieved successfully.", user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Username not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid request or decryption failed.", null));
        }
    }
}
