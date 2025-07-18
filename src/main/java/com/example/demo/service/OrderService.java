package com.example.demo.service;

import com.example.demo.demo.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(String username, List<Long> bookIds);
    List<Order> getOrdersByUsername(String username);
}
