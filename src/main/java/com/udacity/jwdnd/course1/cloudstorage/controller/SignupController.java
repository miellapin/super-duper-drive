package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUp(Model model, @ModelAttribute("newUser") Users newUser) {
        return "signup";
    }

    @PostMapping
    public String signUp(Model model, @ModelAttribute("newUser") Users newUser) {
        String message = null;
        String username = newUser.getUsername();
        if(userService.getUserByUsername(username) != null) {
            message = "This username is already in use!";
        }
        int id = userService.createUser(newUser);
        if(id < 0) {
            message = "An error occured during user creation!";
        }
        if(message == null) {
            model.addAttribute("signupSuccess", true);
        }
        else {
            model.addAttribute("signupError", message);
        }
        return "signup";
    }
}
