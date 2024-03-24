package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u from User u left join fetch u.roleList where u.username=:userName")
    User findByUsername(String userName);

    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

    @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName WHERE u.id = :id")
    void updateUser(String firstName, String lastName, Long id);
}
