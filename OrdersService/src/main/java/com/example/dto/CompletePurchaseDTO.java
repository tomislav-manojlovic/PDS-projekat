package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompletePurchaseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;

    private Integer orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
}
