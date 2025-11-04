package com.example.controller;

import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orders not found");
        }
        List<OrderDTO> orderDTOs = orders.stream()
                .map(o -> modelMapper.map(o, OrderDTO.class))
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order has been created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        orderRepository.delete(order);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order has been deleted");
    }

    @PatchMapping("/total")
    public ResponseEntity<?> updateOrderTotal(@Valid @RequestBody OrderDTO dto) {
        Order order = orderRepository.findById(dto.getId()).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        order.setTotalAmount(dto.getTotalAmount());
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order has been updated");
    }
}
