package com.wilff.gains_spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilff.gains_spring.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> getByEmail(String email);
    
}
