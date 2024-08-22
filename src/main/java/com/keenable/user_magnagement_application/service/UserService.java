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
}

//Yecoode sahi but delete user ko retrieve karne dikat ho rhi

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

    public String decryptPassword(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }

    // Find a deleted username using the decrypted username
    public String findDeletedUsername(String decryptedUsername) {
        if (deletedUsernames.contains(decryptedUsername)) {
            return decryptedUsername;
        } else {
            return null; // Return null or throw an exception if the username is not found
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

/*
package com.keenable.user_management_application.service;

import com.keenable.user_management_application.model.UserEntity;
import com.keenable.user_management_application.repository.UserRepo;
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

    // Check if a username already exists
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
        return userRepo.findByUserName(username)
                       .orElseThrow(() -> new RuntimeException("User not found!"));
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
        updatedUser.setRole(user.getRole());
        updatedUser.setSalary(user.getSalary());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        return userRepo.save(updatedUser);
    }

    // Delete a user by username
    public void deleteUser(String username) {
        UserEntity user = userRepo.findByUserName(username)
                                  .orElseThrow(() -> new RuntimeException("User not found!"));
        userRepo.delete(user);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    // Decrypt the password and return the original username
    public String decryptPassword(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }

    // Retrieve a deleted username by its decrypted value
    public String findDeletedUsername(String decryptedUsername) {
        if (getAllDeletedUsernames().contains(decryptedUsername)) {
            return decryptedUsername;
        } else {
            return null; // Return null or throw an exception if the username is not found
        }
    }

    // Get all usernames (simulating retrieving all deleted usernames)
    public List<String> getAllDeletedUsernames() {
        return userRepo.findAll().stream()
                       .map(UserEntity::getUserName)
                       .collect(Collectors.toList());
    }

    // Retrieve a deleted username by encrypted password
    public String getDeletedUsernameByEncryptedPassword(String encryptedPassword) {
        // Decrypt the password to get the username
        String username = decrypt(encryptedPassword);

        // Check if the decrypted username is in the list of deleted usernames
        if (getAllDeletedUsernames().contains(username)) {
            return username;
        } else {
            return null; // or throw an exception if preferred
        }
    }
}
/code working but aslo save password
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
        user.setPassword(encrypt(user.getUserName())); // Set the encrypted username as the password
        return userRepo.save(user);
    }

    // Retrieve a user by username
    public UserEntity getUserByUsername(String username) {
        return userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Retrieve all users
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    // Update a user's details
    public UserEntity updateUser(String username, UserEntity userData) {
        UserEntity existingUser = getUserByUsername(username);

        if (userData.getEmailId() != null) {
            existingUser.setEmailId(userData.getEmailId());
        }
        // if (userData.getPassword() != null) {
        //     existingUser.setPassword(encrypt(userData.getPassword())); // Encrypt the password if provided
        // }
        if(userData.getSalary()!=0.0){
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

    // Delete a user by username
    public boolean deleteUserByUsername(String username) {
        Optional<UserEntity> userOpt = userRepo.findByUserName(username);
        if (userOpt.isPresent()) {
            userRepo.delete(userOpt.get());
            return true;
        } else {
            return false;
        }
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    // Retrieve a username using encrypted password
    public String retrieveUsername(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }
    public String decryptPassword(String encryptedPassword) {
        return decrypt(encryptedPassword);
    }
    
    // Find a username by decrypting and checking if it exists
    public String findUsername(String decryptedUsername) {
        // Check if the username exists in the active users
        Optional<UserEntity> activeUser = userRepo.findByUserName(decryptedUsername);
        if (activeUser.isPresent()) {
            return activeUser.get().getUserName();
        }
        return null;
    }
}
*/
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
