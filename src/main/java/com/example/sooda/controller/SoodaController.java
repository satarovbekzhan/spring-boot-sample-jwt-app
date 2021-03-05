package com.example.sooda.controller;

import com.example.sooda.entity.User;
import com.example.sooda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SoodaController {

    private final UserService userService;

    @Autowired
    public SoodaController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> getUsersList() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
}
