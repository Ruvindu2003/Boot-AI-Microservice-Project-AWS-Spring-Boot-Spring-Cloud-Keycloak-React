package com.example.demo.dto;

import com.example.demo.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserResponse {


    private String name;
    private String lastName;
    @NotBlank(message = "Not Requerid")
    @Email(message = "Invalid ForMat")
    private String email;
    @NotBlank(message = "Password is a Requeied")
    @Size(min = 6,message = "password must  have 6 chacterse")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
