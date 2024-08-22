/*package com.keenable.user_magnagement_application.controller;

import com.keenable.user_magnagement_application.dto.UserData;
import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/register/{userName}")
    public ResponseEntity<?> createUser(@RequestBody UserData userData) {

    
        if (userService.doesUserNameExist(userData.getUserName())) {
            // Username already exists, return a conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUserName(userData.getUserName());
        user.setEmailId(userData.getEmailId());
        user.setRole(userData.getRole());
        user.setSalary(userData.getSalary());
        user.setPhoneNumber(userData.getPhoneNumber());
        // Password is generated automatically in UserService
        UserEntity createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Retrieve a user all user or by username
    @GetMapping("/retrieve or /retrieve/{username}")
    public ResponseEntity<UserEntity> getUserByUsername(@RequestParam String encryptedPassword) {
        UserEntity user = userService.getUserByUsername(encryptedPassword);
        return ResponseEntity.ok(user);
    }

    // Update user details by username
@PutMapping("/update/{username}")
public ResponseEntity<UserEntity> updateUser(
        @PathVariable String username, 
        @RequestBody UserData userData) {

    // Fetch the existing user by username
    UserEntity existingUser = userService.getUserByUsername(username);
    
    if (existingUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    // Update the existing user's details with the new data
    existingUser.setEmailId(userData.getEmailId());
    existingUser.setPassword(userData.getPassword()); // Optional
    existingUser.setRole(userData.getRole());
    existingUser.setSalary(userData.getSalary());
    existingUser.setPhoneNumber(userData.getPhoneNumber());

    // Save the updated user
    UserEntity updatedUser = userService.updateUser(existingUser);

    return ResponseEntity.ok(updatedUser);
}

    // Delete a user all user or delete by username


    @DeleteMapping("/delete or /delete/{username}")
    
    public ResponseEntity<Void> deleteUser(@RequestParam String encryptedPassword) {
        userService.deleteUser(encryptedPassword);
        return ResponseEntity.noContent().build();
    }

    // Retrieve a deleted user's username
    @GetMapping("/retrieve?passward=value")
    public ResponseEntity<String> retrieveUsername(@RequestParam String encryptedPassword) {
        String username = userService.retrieveUsername(encryptedPassword);
        return ResponseEntity.ok(username);
    }
}



package com.keenable.user_magnagement_application.controller;

//import com.keenable.user_magnagement_application.dto.UserData;
import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.service.UserService;

//import org.hibernate.mapping.Map;
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

    // Create a new user
    /*@PostMapping("/register/{username}")
    public ResponseEntity<?> createUser(@PathVariable String username, @RequestBody UserEntity userData) {

        if (userService.doesUserNameExist(userData.getUserName())) {
            // Username already exists, return a conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUserName(userData.getUserName());
        user.setEmailId(userData.getEmailId());
        user.setRole(userData.getRole());
        user.setSalary(userData.getSalary());
        user.setPhoneNumber(userData.getPhoneNumber());
        // Password is generated automatically in UserService
        UserEntity createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    ye CODE SAHI HAI 
    @PostMapping("/register/{username}")
public ResponseEntity<?> createUser(@PathVariable String username, @RequestBody UserEntity userData) {

    // Check if the username in the URL matches the one in the request body
    if (!username.equals(userData.getUserName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Username in the URL and request body must match");
    }

    // Check if the username already exists
    if (userService.doesUserNameExist(username)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("Username already exists");
    }

    // Set the username from the path variable (just to ensure consistency)
    userData.setUserName(username);

    // Create the user with the service
    UserEntity createdUser = userService.createUser(userData);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
}
//YE CODE BHI CHAL BUT MUJHE MASSGE MILNAA CHAHIYE
//FINE
@PostMapping("/register/{username}")
public ResponseEntity<?> createUser(@PathVariable String username, @RequestBody UserEntity userData) {

    // Define a regex pattern to allow usernames that are either alphabetic, numeric, or alphanumeric
    String usernamePattern = "^[a-zA-Z0-9]+$";

    // Validate the username format using the pattern
    if (!username.matches(usernamePattern)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Invalid username format. Only alphabetic, numeric, or alphanumeric characters are allowed.");
    }

    // Check if the username in the URL matches the one in the request body
    if (!username.equals(userData.getUserName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Username in the URL and request body must match.");
    }

    // Check if the username already exists
    if (userService.doesUserNameExist(username)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("Username already exists.");
    }

    // Set the username from the path variable (just to ensure consistency)
    userData.setUserName(username);

    // Create the user with the service
    UserEntity createdUser = userService.createUser(userData);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
}


    // Retrieve all users or a specific user by username

    
    




   // Endpoint to retrieve all users
   @GetMapping("/retrieve")
   public ResponseEntity<?> getAllUsers() {
       List<UserEntity> users = userService.getAllUsers();
       if (users.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
       }
       return ResponseEntity.ok(users);
   }

   // Endpoint to retrieve a specific user by username
   @GetMapping("/retrieve/{username}")
   public ResponseEntity<?> getUser(@PathVariable String username) {
       UserEntity user = userService.getUserByUsername(username);
       if (user != null) {
           return ResponseEntity.ok(user);
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
       }
   }




    // Delete all users or a specific user by username
     // Endpoint to delete all users
     @DeleteMapping("/delete")
     public ResponseEntity<?> deleteAllUsers() {
         userService.deleteAllUsers();
         return ResponseEntity.noContent().build();
     }
 
     // Endpoint to delete a specific user by username
     @DeleteMapping("/delete/{username}")
     public ResponseEntity<?> deleteUser(@PathVariable String username) {
         boolean isDeleted = userService.deleteUserByUsername(username);
         if (isDeleted) {
             return ResponseEntity.noContent().build();
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("User not found");
         }
     }
 
    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(
            @PathVariable String username, 
            @RequestBody UserEntity userData) {

        // Fetch the existing user by username
        UserEntity existingUser = userService.getUserByUsername(username);
        
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("User not found");
        }

        // Update the existing user's details with the new data
        if (userData.getEmailId() != null) {
            existingUser.setEmailId(userData.getEmailId());
        }
        if (userData.getPassword() != null) {
            existingUser.setPassword(userData.getPassword()); // Optional
        }
        if (userData.getRole() != null) {
            existingUser.setRole(userData.getRole());
        }
        // if (userData.getSalary() != null) {
        //     existingUser.setSalary(userData.getSalary());
        // }
        if (userData.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userData.getPhoneNumber());
        }

        // Save the updated user
        UserEntity updatedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(updatedUser);
    }


    // Retrieve a deleted user's username


    // Retrieve a deleted user's username using encrypted password
    @GetMapping("/getuser")
    public ResponseEntity<?> retrieveDeletedUsername(@RequestParam(required = false) String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            // Retrieve all deleted usernames
            List<String> deletedUsernames = userService.getAllDeletedUsernames();
            return ResponseEntity.ok(deletedUsernames);
        } else {
            // Retrieve a specific deleted username using encrypted password
            String decryptedUsername = userService.decryptPassword(encryptedPassword);
            String deletedUsername = userService.findDeletedUsername(decryptedUsername);
            
            if (deletedUsername != null) {
                return ResponseEntity.ok(deletedUsername);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Deleted user not found");
            }
        }
    }
}
Sahi hai but deletation ka maessage nhi aa rha hai
package com.keenable.user_magnagement_application.controller;

import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.service.UserService;
//import com.keenable.user_magnagement_application.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity.BodyBuilder;
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
                    .body(new ApiResponse(e.getMessage(), null));
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
            return  ResponseEntity.status(HttpStatus.NO_CONTENT)
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
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Retrieve  user's username using encrypted password
    @GetMapping("/retrieve/password/{encryptedPassword}")
    public ResponseEntity<ApiResponse> retrieveUsername(@PathVariable String encryptedPassword) {
        try {
            // Decrypt the password to obtain the username
            String userName = userService.decryptPassword(encryptedPassword);
            System.out.println("Decrypted Username: " + userName);//console it
            // Find the username in the database
            UserEntity username = userService.getUserByUsername(userName);
            //String username = userService.findUsername(decryptedUsername);
             // Log the username being searched
                //System.out.println("Searching for Username: " + user);

            if (username!= null) {
                return ResponseEntity.ok(new ApiResponse("Username retrieved successfully.", username));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Username not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid request or decryption failed.", null));
        }
    }
} */  //for a username
// @GetMapping("/retrieve/password/{encryptedPassword}")
// public ResponseEntity<ApiResponse> retrieveUsername(@PathVariable String encryptedPassword) {
//     try {
//         // Decrypt the password to obtain the username
//         String decryptedUsername = userService.decryptPassword(encryptedPassword);
//         System.out.println("Decrypted Username: " + decryptedUsername); // Log decrypted username
        
//         // Check if the username exists in the database
//         boolean doesExist = userService.doesUserNameExist(decryptedUsername);
        
//         if (doesExist) {
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
