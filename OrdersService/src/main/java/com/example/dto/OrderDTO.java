package com.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Integer id;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    @Size(max = 255, message = "Description can be up to 255 characters long")
    private String description;
}
