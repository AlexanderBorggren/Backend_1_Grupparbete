package com.example.grupparbete_backend_1.dto;

import com.example.grupparbete_backend_1.models.Role;
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

    private Long id;
    private String username;
    private String password;
    private List<Role> roles;
    private boolean enabled;

    public String getRoleString() {
        String myString = "";
        for(Role role : roles)
        {
            myString += role.getName();
            myString += ", ";
        }

        return myString.substring(0, myString.length()-2);
    }

}
