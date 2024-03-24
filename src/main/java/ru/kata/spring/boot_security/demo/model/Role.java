package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;

    public Role(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(long id) {
        this.id = id;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id)
                && Objects.equals(name, role.name)
                && Objects.equals(userList, role.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userList);
    }
}
