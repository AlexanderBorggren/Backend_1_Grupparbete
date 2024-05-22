package com.example.grupparbete_backend_1.dto;

import com.example.grupparbete_backend_1.securities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private List<Role> roles;
    private boolean enabled;

}
