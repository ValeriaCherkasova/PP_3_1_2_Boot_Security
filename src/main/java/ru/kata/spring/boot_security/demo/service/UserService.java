package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(EntityManager em, UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.em = em;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        String pass = passwordEncoder.encode(user.getPassword());

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        userRepository.save(user);

        Role role = roleRepository.findFirstByName("ROLE_USER");
        if (role == null) {
            // Создать новую роль, если она не найдена
            role = new Role("ROLE_USER");
            roleRepository.save(role);
        }

        user.setRoleList(Collections.singletonList(role));

        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        }
    }

    public List<User> getAllUsers() {
        return (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
