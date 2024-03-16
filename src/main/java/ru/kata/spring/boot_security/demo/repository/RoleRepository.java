package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByName(String name);
}
