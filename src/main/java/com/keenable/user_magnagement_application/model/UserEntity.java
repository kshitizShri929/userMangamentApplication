package com.keenable.user_magnagement_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.persistence.Transient;
//import jakarta.persistence.Column;
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
    //private Long id;
    @Column(name="userName")
    private String userName;
    private String emailId;
    
    private String password;
    private String role;
    private double salary;
    private Long phoneNumber;
    @JsonIgnore
    private boolean deleted =false;


}
