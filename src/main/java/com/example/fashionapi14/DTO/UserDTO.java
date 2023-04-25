package com.example.fashionapi14.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;

    private String role;
}
