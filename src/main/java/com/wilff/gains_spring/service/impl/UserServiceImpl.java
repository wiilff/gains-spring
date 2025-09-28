package com.wilff.gains_spring.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wilff.gains_spring.dto.response.UserResponse;
import com.wilff.gains_spring.model.User;
import com.wilff.gains_spring.repository.UserRepository;
import com.wilff.gains_spring.service.interfaces.user.UserCommandService;
import com.wilff.gains_spring.service.interfaces.user.UserQueryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserCommandService, UserQueryService {

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
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.getByEmail(email);
    }
    
}
