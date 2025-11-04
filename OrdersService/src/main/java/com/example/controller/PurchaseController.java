package com.example.controller;

import com.example.dto.CompletePurchaseDTO;
import com.example.dto.OrderDTO;
import com.example.dto.PurchaseDTO;
import com.example.dto.UserDTO;
import com.example.entity.Order;
import com.example.entity.Purchase;
import com.example.repository.OrderRepository;
import com.example.repository.PurchaseRepository;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;
    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<CompletePurchaseDTO>> getPurchases() {

        List<Purchase> purchases = purchaseRepository.findAll();
        List<UserDTO> userDTOS = orderService.fetchAllUsersOrThrow();
        List<CompletePurchaseDTO> completePurchaseDTOS = new ArrayList<>();

        for (Purchase purchase : purchases) {
            CompletePurchaseDTO dto = new CompletePurchaseDTO();

            dto.setOrderId(purchase.getOrderId());
            dto.setUserId(purchase.getUserId());

            Order order = orderRepository.findById(purchase.getOrderId()).orElse(null);
            if (order != null) {
                modelMapper.map(order, dto);
            }

            userDTOS.stream()
                    .filter(u -> u.getId().equals(purchase.getUserId()))
                    .findFirst()
                    .ifPresent(u -> {
                        dto.setUserId(u.getId());
                        dto.setFirstName(u.getFirstName());
                        dto.setLastName(u.getLastName());
                    });

            completePurchaseDTOS.add(dto);
        }

        return ResponseEntity.ok(completePurchaseDTOS);
    }

    @PostMapping
    public ResponseEntity<String> createPurchase(@RequestBody PurchaseDTO purchaseDTO) {

        boolean orderExists = orderRepository.existsById(purchaseDTO.getOrderId());
        if (!orderExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        UserDTO user = orderService.fetchUserOrThrow(purchaseDTO.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Purchase purchase = new Purchase();
        purchase.setOrderId(purchaseDTO.getOrderId());
        purchase.setUserId(purchaseDTO.getUserId());

        purchaseRepository.save(purchase);

        return ResponseEntity.status(HttpStatus.CREATED).body("Purchase has been created");
    }
}
