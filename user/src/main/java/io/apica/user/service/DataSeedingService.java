package io.apica.user.service;

import io.apica.user.entity.Role;
import io.apica.user.entity.User;
import io.apica.user.repo.RoleRepo;
import io.apica.user.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DataSeedingService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct // Execute after dependency injection
    public void seedData() {
        if (roleRepo.count() == 0) {
            Role role1 = new Role(); // Create a Role ADMIN
            role1.setName("ADMIN");
            Role role2 = new Role(); // Create a Role CLIENT
            role2.setName("CLIENT");
            roleRepo.save(role1);
            roleRepo.save(role2);
        }
        if (userRepository.count() == 0) { // Check if there's no existing data
            createRoleAndUser("admin", "ADMIN", "admin123");
            createRoleAndUser("client", "CLIENT", "client123");
        }
    }

    private void createRoleAndUser(String username, String roleName, String password) {
        Role role = roleRepo.findByName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User(); // Create a User object
        user.setRoles(roles);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(username + "_name");
        userRepository.save(user); // Save the user to the database
    }
}