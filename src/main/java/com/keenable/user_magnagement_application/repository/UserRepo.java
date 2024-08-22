package com.keenable.user_magnagement_application.repository;

import com.keenable.user_magnagement_application.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
//import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName );
//     

     

    //List<UserEntity> findByPassword(String password); // Query for deleted users if needed
}


