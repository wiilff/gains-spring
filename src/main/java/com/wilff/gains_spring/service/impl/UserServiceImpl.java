package com.wilff.gains_spring.service.impl;

import java.util.List;
import java.util.Optional;

import com.wilff.gains_spring.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public org.springframework.security.core.userdetails.User save(User user) {
        userRepository.save(user);
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.getByEmail(email);
    }
    
}
