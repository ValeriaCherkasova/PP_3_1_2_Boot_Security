package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final RoleServiceImp roleService;

    public AdminController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/get_all_users")
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping(value = "/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/get_all_users";
    }

    @PostMapping(value = "/save_user")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles") String[] roles) {
        if (Arrays.asList(roles).contains("all")) {
            user.setRoleList(roleService.getAllRoles());
        } else {
            user.setRoleList(roleService.getListOfRoles(roles));
        }
        userService.saveUser(user);
        return "redirect:/admin/get_all_users";
    }

    @PostMapping(value = "/update_user")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles) {
        if (Arrays.asList(roles).contains("all")) {
            user.setRoleList(roleService.getAllRoles());
        } else {
            user.setRoleList(roleService.getListOfRoles(roles));
        }

        userService.updateUser(user);
        return "redirect:/admin/get_all_users";
    }

    @GetMapping(value = "/get_user")
    public String getUser(ModelMap model, @RequestParam("id") Long id) {
        Optional<User> user = userService.findById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user.get());
        return "update_user";
    }

    @GetMapping(value = "/get_roles")
    public String getRoles(ModelMap model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "add_user";
    }
}
