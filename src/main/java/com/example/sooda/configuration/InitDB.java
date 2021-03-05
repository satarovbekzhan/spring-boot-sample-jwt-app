package com.example.sooda.configuration;

import com.example.sooda.entity.Role;
import com.example.sooda.entity.User;
import com.example.sooda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Set;

@Component
public class InitDB {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(InitDB.class);

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public InitDB(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        try {
            User superUser = new User();
            superUser.setUsername("superuser");
            superUser.setPassword(bCryptPasswordEncoder.encode("superuser"));
            superUser.setActive(true);
            superUser.setRoles(Set.of(Role.ADMIN, Role.USER, Role.MANAGER));
            User addedAdmin = userService.addUser(superUser);
            LOGGER.info(MessageFormat.format("Added user id: {0}, username: {1}, password: {2}, roles: {3}!",
                    addedAdmin.getId(), addedAdmin.getUsername(), superUser.getPassword(), addedAdmin.getRoles()));
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot add superuser: " + e.getMessage());
        }

        try {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setActive(true);
            admin.setRoles(Set.of(Role.ADMIN, Role.USER, Role.MANAGER));
            User addedAdmin = userService.addUser(admin);
            LOGGER.info(MessageFormat.format("Added user id: {0}, username: {1}, password: {2}, roles: {3}!",
                    addedAdmin.getId(), addedAdmin.getUsername(), admin.getPassword(), addedAdmin.getRoles()));
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot add admin: " + e.getMessage());
        }

        try {
            User manager = new User();
            manager.setUsername("manager");
            manager.setPassword(bCryptPasswordEncoder.encode("manager"));
            manager.setActive(true);
            manager.setRoles(Set.of(Role.MANAGER));
            User addedManager = userService.addUser(manager);
            LOGGER.info(MessageFormat.format("Added user id: {0}, username: {1}, password: {2}, roles: {3}!",
                    addedManager.getId(), addedManager.getUsername(), manager.getPassword(), addedManager.getRoles()));
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot add manager: " + e.getMessage());
        }

        try {
            User user = new User();
            user.setUsername("user");
            user.setPassword(bCryptPasswordEncoder.encode("user"));
            user.setActive(true);
            user.setRoles(Set.of(Role.USER));
            User addedUser = userService.addUser(user);
            LOGGER.info(MessageFormat.format("Added user id: {0}, username: {1}, password: {2}, roles: {3}!",
                    addedUser.getId(), addedUser.getUsername(), user.getPassword(), addedUser.getRoles()));
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot add user: " + e.getMessage());
        }
    }
}
