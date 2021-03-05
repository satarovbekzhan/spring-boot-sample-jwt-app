package com.example.sooda.service.implementation;

import com.example.sooda.entity.Role;
import com.example.sooda.entity.User;
import com.example.sooda.exception.UserAlreadyExistsException;
import com.example.sooda.exception.UserDoNotExistsException;
import com.example.sooda.exception.UserNotFoundException;
import com.example.sooda.repository.UserRepository;
import com.example.sooda.service.UserService;
import com.example.sooda.util.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("User with username {0} not found!", username)));

        return user.map(UserDetailsImpl::new).get();
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserDoNotExistsException(username)
        );
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public List<User> getUsers(Role role) {
        return userRepository.findUsersByRolesContains(role);
    }

    @Override
    public User deleteUser(String id) {
        User userToDelete = getUser(id);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    @Override
    @Transactional
    public User editUser(String id, User user) {
        User userToEdit = getUser(id);
        if (!userToEdit.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
            userToEdit.setUsername(user.getUsername());
        }
        if (!userToEdit.getPassword().equals(user.getPassword())) {
            userToEdit.setPassword(user.getPassword());
        }
        userToEdit.setActive(user.isActive());
        userToEdit.setRoles(user.getRoles());
        return userToEdit;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}