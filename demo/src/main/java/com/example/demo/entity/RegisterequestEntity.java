package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterequestEntity {

        private String name;
        private String lastName;
        @NotBlank(message = "Not Requerid")
        @Email(message = "Invalid ForMat")
        private String email;
        @NotBlank(message = "Password is a Requeied")
        @Size(min = 6,message = "password must  have 6 chacterse")
        private String password;


}
