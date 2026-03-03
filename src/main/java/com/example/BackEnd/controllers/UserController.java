package com.example.BackEnd.controllers;

import com.example.BackEnd.entities.User;
import com.example.BackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:4201"})
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        try {
            User savedUser = service.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        String emailOrUsername = (loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty())
                ? loginRequest.getEmail() : loginRequest.getUsername();
        return service.login(emailOrUsername, loginRequest.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable int id, @RequestBody java.util.Map<String, String> body) {
        String role = body != null ? body.get("role") : null;
        return service.updateUserRole(id, role != null ? role.trim() : "")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody java.util.Map<String, String> body) {
        if (body == null) return ResponseEntity.badRequest().build();
        String name = body.get("name");
        String surname = body.get("surname");
        String email = body.get("email");
        String phone = body.get("phone");
        return service.updateUser(id, name, surname, email, phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(){
        service.deleteAll();
        return "Table formated";
    }

}
