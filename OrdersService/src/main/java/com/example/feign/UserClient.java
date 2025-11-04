package com.example.feign;

import com.example.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "users-service")
public interface UserClient {

    @GetMapping("/users")
    public List<UserDTO> getUsers();

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Integer id);
}