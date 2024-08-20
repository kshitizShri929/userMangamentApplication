package com.keenable.user_magnagement_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Long id;
    private String userName;
    private String emailId;
    private String password; // Password is included in responses where applicable
    private String role;
    private double salary;
    private Long phoneNumber;
}

