package com.project.demo.controller;

import com.project.demo.model.User;
import com.project.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/users")
@Slf4j
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        User createdUser = userService.createNewUser(user);
        if (Objects.isNull(createdUser)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


}
