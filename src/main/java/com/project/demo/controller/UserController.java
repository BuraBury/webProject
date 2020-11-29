package com.project.demo.controller;

import com.project.demo.model.User;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String users(ModelMap modelMap) {
        modelMap.addAttribute("usersList", userService.getAllUser());
        return "user";
    }

    @GetMapping("/user/{id}")
    public String user(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "one-user";
    }

    @PostMapping("/user/add")
    public String addUser(@Valid @ModelAttribute("user") User user, final Errors errors) {
        if (errors.hasErrors()) {
            return "user-add";
        }
        userService.createNewUser(user);
        return "succeded";
    }

    @GetMapping("/user/add")
    public String showUserAdd(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("error-msg", "błąd danych");
        return "user-add";
    }

}
