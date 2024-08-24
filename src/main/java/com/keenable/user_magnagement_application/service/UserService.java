
package com.keenable.user_magnagement_application.service;

import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final int SHIFT = 4; // Caesar cipher shift value

    // Encrypt username to generate password
    private String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char i : input.toCharArray()) {
            if (Character.isLetter(i)) {
                char base = Character.isLowerCase(i) ? 'a' : 'A';
                encrypted.append((char) ((i - base + SHIFT) % 26 + base));
            } else if (Character.isDigit(i)) {
                encrypted.append((char) ((i - '0' + SHIFT) % 10 + '0'));
            } else {
                encrypted.append(i);
            }
        }
        return encrypted.toString();
    }

    // Decrypt the password
    private String decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (char i : input.toCharArray()) {
            if (Character.isLetter(i)) {
                char base = Character.isLowerCase(i) ? 'a' : 'A';
                decrypted.append((char) ((i - base - SHIFT + 26) % 26 + base));
            } else if (Character.isDigit(i)) {
                decrypted.append((char) ((i - '0' - SHIFT + 10) % 10 + '0'));
            } else {
                decrypted.append(i);
            }
        }
        return decrypted.toString();
    }

    // Create a new user with an encrypted password
    public UserEntity createUser(UserEntity user) {
        String encryptedPassword = null;
        if (user.getUserName() != null) {
            encryptedPassword = encrypt(user.getUserName()); // Encrypt username to create password
            user.setPassword(encryptedPassword); // Set the encrypted password temporarily
        }
        UserEntity savedUser = userRepo.save(user);
        savedUser.setPassword(encryptedPassword); // Include the encrypted password only in the registration response
        return savedUser;
    }

    // Retrieve a user by username
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setPassword(null); // Exclude password from the response
        return user;
    }

    // Retrieve all users
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        // Exclude password from all user entities in the list
        for (UserEntity user : users) {
            user.setPassword(null);
        }
        return users;
    }

    // Update a user's details
    public UserEntity updateUser(String username, UserEntity userData) {
        UserEntity existingUser = getUserByUsername(username);

        if (userData.getEmailId() != null) {
            existingUser.setEmailId(userData.getEmailId());
        }
        if (userData.getSalary() != 0.0) {
            existingUser.setSalary(userData.getSalary());
        }
        if (userData.getRole() != null) {
            existingUser.setRole(userData.getRole());
        }
        if (userData.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userData.getPhoneNumber());
        }

        return userRepo.save(existingUser);
    }

    // // Delete a user by username
    // public boolean deleteUserByUsername(String username) {
    //     Optional<UserEntity> userOpt = userRepo.findByUserName(username);
    //     if (userOpt.isPresent()) {
    //         userRepo.delete(userOpt.get());
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

    // // Delete all users
    // public void deleteAllUsers() {
    //     userRepo.deleteAll();
    // }


    public boolean deleteUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepo.findByUserNameAndDeletedFalse(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setDeleted(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public void deleteAllUsers() {
        List<UserEntity> users = userRepo.findByDeletedFalse();
        users.forEach(user -> {
            user.setDeleted(true);
            userRepo.save(user);
        });
    }

    // Retrieve a username using encrypted password
    public String retrieveUsername(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }
    
    // Find a username by decrypting and checking if it exists
    public String findUsername(String encryptedPassword) {
        // Decrypt the encrypted password to retrieve the username
        String username = decrypt(encryptedPassword);
        
        // Find the user by the decrypted username
        Optional<UserEntity> activeUser = userRepo.findByUserName(username);
        //return activeUser;
         //Return the username if the user is found, otherwise return null
        return activeUser.map(UserEntity::getUserName).orElse(null);
    }
    
    // Check if the username exists in the database
    public boolean doesUserNameExist(String userName) {
        // Return true if a user with the specified username exists, otherwise return false
        return userRepo.findByUserName(userName).isPresent();
    }

    // Decrypt the password
    public String decryptPassword(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }
}
