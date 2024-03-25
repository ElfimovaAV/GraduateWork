package ru.gb.backend.dto;

import lombok.*;
import ru.gb.backend.models.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
