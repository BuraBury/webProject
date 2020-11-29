package com.project.demo.service;

import com.project.demo.model.User;

import java.util.List;

public interface UserService {
    User createNewUser(User user);

    List<User> getAllUser();


    User getUserById(Long id);
}
