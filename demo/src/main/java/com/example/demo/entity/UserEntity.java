package com.example.demo.entity;

import com.example.demo.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.generator.values.GeneratedValues;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserRole userRole;
}
