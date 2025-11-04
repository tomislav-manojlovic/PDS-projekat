package com.example.service;

import com.example.feign.UserClient;
import com.example.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserClient usersClient;

    private static final String USER_SERVICE_CB = "userServiceClient";
    private static final String USER_SERVICE_RETRY = "userServiceClient";

    @Retry(name = USER_SERVICE_RETRY)
    @CircuitBreaker(name = USER_SERVICE_CB, fallbackMethod = "getUserFallback")
    public UserDTO fetchUserOrThrow(Integer userId) {
        log.info("Calling users-service (Feign) with userId=" + userId);
        return usersClient.getUserById(userId);
    }

    public UserDTO getUserFallback(Integer userId, Throwable t) {
        log.warn("CB fallback while fetching user with id=" + userId + " cause=" + t.toString());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Users service is currently unavailable (CB fallback)", t
        );
    }

    @Retry(name = USER_SERVICE_RETRY)
    @CircuitBreaker(name = USER_SERVICE_CB, fallbackMethod = "getAllUsersFallback")
    public List<UserDTO> fetchAllUsersOrThrow() {
        log.info("Calling users-service (Feign) to fetch all users");
        return usersClient.getUsers();
    }

    public List<UserDTO> getAllUsersFallback(Throwable t) {
        log.warn("CB fallback while fetching all users cause=" + t.toString());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Users service is currently unavailable (CB fallback)", t
        );
    }
}
