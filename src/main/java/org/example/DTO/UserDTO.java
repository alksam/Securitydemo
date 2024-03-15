package org.example.DTO;

import lombok.*;
import org.example.Entity.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String username;
    private String password;
    Set<String> roles = new HashSet<>();
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserDTO(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
    }
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = user.getRoles()
                .stream()
                .filter(Objects::nonNull)
                .map(role -> role.getRoleName())
                .collect(java.util.stream.Collectors.toSet());
    }
    public void addRole(String role) {
        roles.add(role);
    }

}
