package com.keenable.user_magnagement_application.model;

import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="emp_user")
public class UserEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
   // private Long id;
    
    private String userName;
    private String emailId;
    private String password;
    private String role;
    private double salary;
    private Long phoneNumber;
    


}
