package io.apica.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {
    @NonNull
    Long id;
    @NonNull
    String username;
    @NonNull
    String name;
    @NonNull
    String password;
    List<String> roleNames = new ArrayList<>();
}
