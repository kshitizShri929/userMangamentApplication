package com.keenable.user_magnagement_application.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name="userName")
    private String userName;
    private String emailId;
    private String password;
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.0####################") //for Ignore E notettion
    private Double salary;
    //private String salary;
    private Long phoneNumber;
    @JsonIgnore
    private Boolean deleted =false;


}
