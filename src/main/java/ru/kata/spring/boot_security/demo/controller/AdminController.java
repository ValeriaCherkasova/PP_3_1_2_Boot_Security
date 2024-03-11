package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

public class AdminController {
    private UserService userService;

    @GetMapping(value = "/get_all_users")
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/read_user")
    public String readUser(@RequestParam("id") Long id, Model model) {
        User user = userService.readUser(id);
        model.addAttribute("user", user);
        return "read_user";
    }

    @PostMapping(value = "/create_user")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/user/get_all_users";
    }

    @PostMapping(value = "/update_user")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/user/get_all_users";
    }

    @PostMapping(value = "/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/user/get_all_users";
    }
}
