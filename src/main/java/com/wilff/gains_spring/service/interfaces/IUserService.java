package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void save(User user);
    User getById(int id);
    Optional<User> findByEmail(String email);
    List<User> getAll();

}
