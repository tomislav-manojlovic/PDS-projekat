package com.example.controller;

import com.example.dto.UserEmailChangedDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        }
        List<UserDTO> userDTOs = users.stream().map(u -> modelMapper.map(u, UserDTO.class)).toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User has not been found");
        }
        UserDTO userDTOs = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User has not been found");
        }
        userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has been deleted");
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateUser(@RequestBody UserEmailChangedDTO dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User has not been found");
        }
        user.setEmail(dto.getEmail());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has been updated");
    }
}
