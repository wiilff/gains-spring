package com.wilff.gains_spring.service.interfaces.user;

import java.util.List;
import java.util.Optional;

import com.wilff.gains_spring.model.User;

public interface UserQueryService {
    
    User getById(int id);
    Optional<User> findByEmail(String email);
    List<User> getAll();
}
