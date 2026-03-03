package com.example.BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private int id;

    @Column(name = "Name")
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @Column(name = "Surname")
    @Size(max = 100)
    private String surname;

    @Column(name = "Email")
    @NotBlank(message = "Email is required")
    @Size(max = 255)
    private String email;

    @Column(name = "Username")
    @NotBlank(message = "Username is required")
    @Size(max = 100)
    private String username;

    @Column(name = "Password")
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "Role")
    private String role = "USER"; // USER or ADMIN

    @Column(name = "Phone")
    @Size(max = 30)
    private String phone; // WhatsApp / contact number
}
