package com.example.BackEnd.services;

import com.example.BackEnd.entities.User;
import com.example.BackEnd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User saveUser(User user) {
        if (user.getId() == 0) {
            repository.findByEmail(user.getEmail())
                    .ifPresent(u -> { throw new IllegalArgumentException("Email already registered"); });
            repository.findByUsername(user.getUsername())
                    .ifPresent(u -> { throw new IllegalArgumentException("Username already taken"); });
        }
        if (repository.count() == 0) {
            user.setRole("ADMIN"); // First user becomes admin
        } else if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return repository.save(user);
    }

    public Optional<User> login(String usernameOrEmail, String password) {
        Optional<User> byUsername = repository.findByUsernameAndPassword(usernameOrEmail, password);
        if (byUsername.isPresent()) return byUsername;
        return repository.findByEmailAndPassword(usernameOrEmail, password);
    }
    public List<User> getAllUsers(){
        return repository.findAll();
    }
    public void deleteAll(){
        repository.deleteAll();
    }

    public Optional<User> updateUserRole(int id, String role) {
        if (!"ADMIN".equals(role) && !"USER".equals(role)) {
            return Optional.empty();
        }
        return repository.findById(id)
                .map(user -> {
                    user.setRole(role);
                    return repository.save(user);
                });
    }

    /** Update user profile fields (name, surname, email, phone). Does not change password or role. */
    public Optional<User> updateUser(int id, String name, String surname, String email, String phone) {
        return repository.findById(id)
                .map(user -> {
                    if (name != null) user.setName(name.trim());
                    if (surname != null) user.setSurname(surname.trim());
                    if (email != null && !email.isBlank()) user.setEmail(email.trim());
                    if (phone != null) user.setPhone(phone.trim().isEmpty() ? null : phone.trim());
                    return repository.save(user);
                });
    }
}
