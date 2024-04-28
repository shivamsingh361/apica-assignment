package io.apica.user.controller;

import io.apica.user.dto.UserReqDTO;
import io.apica.user.dto.UserResDTO;
import io.apica.user.entity.User;
import io.apica.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResDTO> register(@RequestBody UserReqDTO userDto) {
        userDto.setRoleNames(Collections.singletonList("CLIENT"));
        return ResponseEntity.ok(userService.register(userDto));
    }
    @PutMapping("/update")
    public ResponseEntity<UserResDTO> update(@RequestBody UserReqDTO userDto) {
        userDto.setRoleNames(Collections.singletonList("CLIENT"));
        return ResponseEntity.ok(userService.update(userDto));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResDTO> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.fetch(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long id) {
        return userService.delete(id) ?
                ResponseEntity.ok(String.format("User with id: %s deleted!", id)) :
                ResponseEntity.ok(String.format("Failed to delete User with id: %s", id));
    }
}
