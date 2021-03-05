package com.example.sooda.service;

import com.example.sooda.entity.Role;
import com.example.sooda.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User addUser(User user);
    User getUser(String id);
    User getUserByUsername(String username);
    List<User> getUsers();
    List<User> getUsers(Role role);
    User deleteUser(String id);
    User editUser(String id, User user);
    boolean existsByUsername(String username);
}
