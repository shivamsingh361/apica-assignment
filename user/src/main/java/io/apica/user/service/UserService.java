package io.apica.user.service;

import io.apica.user.dto.UserReqDTO;
import io.apica.user.dto.UserResDTO;

import java.util.List;

public interface UserService {
    UserResDTO register(UserReqDTO userDto);
    UserResDTO update(UserReqDTO userDto);
    UserResDTO fetch(Long id);
    List<UserResDTO> fetchAll();
    Boolean delete(Long id);

}
