/*package com.keenable.user_magnagement_application.service;

import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final int SHIFT = 3; // Caesar cipher shift value

    public static final String existingUser = null;

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

    // Decrypt the password to retrieve the original username
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

    public boolean doesUserNameExist(String userName) {
        return userRepo.findByUserName(userName).isPresent();
    }

    // Create a new user
    public UserEntity createUser(UserEntity user) {
        // Encrypt the username to generate the password
        user.setPassword(encrypt(user.getUserName())); // Set the encrypted username as the password
        return userRepo.save(user);
    }

    // Retrieve a user by username
    public UserEntity getUserByUsername(String encryptedPassword) {
        String username = decrypt(encryptedPassword); // Decrypt the password to get the username
        return userRepo.findByUserName(username).orElseThrow(() -> 
            new RuntimeException("User not found!"));
    }

    // Update a user's details
    public UserEntity updateUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepo.findByUserName(user.getUserName());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        UserEntity updatedUser = existingUser.get();
        updatedUser.setEmailId(user.getEmailId());
        updatedUser.setPassword(user.getPassword()); // Update password if necessary
        updatedUser.setRole(user.getRole());
        updatedUser.setSalary(user.getSalary());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        return userRepo.save(updatedUser);
    }

    // Delete a user by username (Note: the username cannot be reused even after deletion)
    public void deleteUser(String encryptedPassword) {
        String username = decrypt(encryptedPassword); // Decrypt the password to get the username
        UserEntity user = userRepo.findByUserName(username).orElseThrow(() -> 
            new RuntimeException("User not found!"));
        userRepo.delete(user);
    }

    // Retrieve a deleted user by username (this method would allow retrieval of the username only, not full details)
    public String retrieveUsername(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }
}*/



package com.keenable.user_magnagement_application.service;

import com.keenable.user_magnagement_application.model.UserEntity;
import com.keenable.user_magnagement_application.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final int SHIFT = 3; // Caesar cipher shift value

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

    // Decrypt the password to retrieve the original username
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

    public boolean doesUserNameExist(String userName) {
        return userRepo.findByUserName(userName).isPresent();
    }

    // Create a new user
    public UserEntity createUser(UserEntity user) {
        // Encrypt the username to generate the password
        user.setPassword(encrypt(user.getUserName())); // Set the encrypted username as the password
        return userRepo.save(user);
    }

    // Retrieve a user by username
    public UserEntity getUserByUsername(String username) {
        return userRepo.findByUserName(username).orElseThrow(() -> 
            new RuntimeException("User not found!"));
    }

    // Retrieve all users
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    // Update a user's details
    public UserEntity updateUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepo.findByUserName(user.getUserName());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        UserEntity updatedUser = existingUser.get();
        updatedUser.setEmailId(user.getEmailId());
        //updatedUser.setPassword(user.getPassword()); // Update password if necessary
        updatedUser.setRole(user.getRole());
        updatedUser.setSalary(user.getSalary());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        return userRepo.save(updatedUser);
    }

    // Delete a user by username (Note: the username cannot be reused even after deletion)
    public void deleteUser(String username) {
        UserEntity user = userRepo.findByUserName(username).orElseThrow(() -> 
            new RuntimeException("User not found!"));
        userRepo.delete(user);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    // Retrieve a deleted username (simulated as getting the original username from the encrypted password)
    public String retrieveDeletedUsername(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }

    // Retrieve all deleted usernames (simulated by returning all usernames)
    public List<String> getAllDeletedUsernames() {
        return userRepo.findAll().stream()
                .map(UserEntity::getUserName)
                .collect(Collectors.toList());
    }

    // Delete a specific user by username and return if it was deleted
    public boolean deleteUserByUsername(String username) {
        Optional<UserEntity> userOpt = userRepo.findByUserName(username);
        if (userOpt.isPresent()) {
            userRepo.delete(userOpt.get());
            return true;
        } else {
            return false;
        }
    }

    // Get a specific deleted username by encrypted password
    public String getDeletedUsername(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }

    // Get a specific deleted username by encrypted password
    public String getDeletedUsernameByEncryptedPassword(String encryptedPassword) {
        // Decrypt the password to get the username
        String username = decrypt(encryptedPassword);
    
        // Check if the decrypted username is in the list of deleted usernames
        if (getAllDeletedUsernames().contains(username)) {
            return username;
        } else {
            return null; // or throw an exception if you prefer
        }
    }
}
