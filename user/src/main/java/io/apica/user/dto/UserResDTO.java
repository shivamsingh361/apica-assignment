package io.apica.user.dto;

import io.apica.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResDTO {
    String username;
    String name;
    Long id;
    Set<Role> roles;
}
