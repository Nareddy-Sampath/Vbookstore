package com.example.demo.controller;

import com.example.demo.demo.Order;
import com.example.demo.dto.OrderRequest;
import com.example.demo.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public Order placeOrder(@RequestBody OrderRequest request,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.placeOrder(userDetails.getUsername(), request.getBookIds());
    }

    @GetMapping("/my")
    public List<Order> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return orderService.getOrdersByUsername(userDetails.getUsername());
    }
}
