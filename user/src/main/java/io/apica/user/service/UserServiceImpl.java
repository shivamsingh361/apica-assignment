package io.apica.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.apica.user.dto.UserReqDTO;
import io.apica.user.dto.UserResDTO;
import io.apica.user.entity.User;
import io.apica.user.entity.UserEvent;
import io.apica.user.repo.RoleRepo;
import io.apica.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public UserResDTO register(UserReqDTO userDto) {
        User user = objectMapper.convertValue(userDto, User.class);
        if (CollectionUtils.isEmpty(userDto.getRoleNames())) {
            log.error("Roles are required!");
            // Throw Custom Validation Exception with 400
            throw new RuntimeException("Roles are required!");
        } else {
            user.setRoles(userDto.getRoleNames().stream().map(role->roleRepo.findByName(role)).collect(Collectors.toSet()));
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepo.save(user);
        kafkaProducerService.send("create-user-events", user.getId().toString(), new UserEvent("CREATED", user.getId()));
        return objectMapper.convertValue(user, UserResDTO.class);
    }

    @Override
    public UserResDTO update(UserReqDTO userDto) {
        Optional<User> userOpt = userRepo.findById(userDto.getId());
        if (userOpt.isEmpty()) throw new RuntimeException("Id is dosen't exist");
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        User savedUser = userRepo.save(user);
        kafkaProducerService.send("update-user-events", savedUser.getId().toString(), new UserEvent("UPDATED", savedUser.getId()));
        return objectMapper.convertValue(savedUser, UserResDTO.class);
    }

    @Override
    public UserResDTO fetch(Long id) {
        Optional<User> user = userRepo.findById(id);
        kafkaProducerService.send("fetch-user-events", id.toString(), new UserEvent("FETCH", id));
        return user.map(value -> objectMapper.convertValue(value, UserResDTO.class)).orElse(null);
    }

    @Override
    public List<UserResDTO> fetchAll() {
        kafkaProducerService.send("fetch_all-user-events", "NA", new UserEvent("FETCH_ALL", 0L));
        return userRepo.findAll().stream().map(usr->objectMapper.convertValue(usr, UserResDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        userRepo.deleteById(id);
        kafkaProducerService.send("delete-user-events", id.toString(), new UserEvent("DELETE", id));
        return true;
    }

    /**
     * This method is being used for auth by springSecurity
     * @param username the username identifying the user whose data is required.
     * @return UserDetails obj
     * @throws UsernameNotFoundException ex
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
