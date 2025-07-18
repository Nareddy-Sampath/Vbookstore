package com.example.demo.service;

import com.example.demo.demo.Book;
import com.example.demo.demo.Order;
import com.example.demo.demo.OrderItem;
import com.example.demo.demo.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            BookRepository bookRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order placeOrder(String username, List<Long> bookIds) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Book> books = bookRepository.findAllById(bookIds);
        if (books.isEmpty()) {
            throw new RuntimeException("No valid books found.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> items = books.stream().map(book -> {
            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setQuantity(1);  // default quantity
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }
}
