package ru.bsuedu.cad.lab.service;

import org.springframework.stereotype.Service;
import ru.bsuedu.cad.lab.model.Order;
import ru.bsuedu.cad.lab.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order create(Order order) {
        validateOrder(order);
        return orderRepository.save(order);
    }

    public boolean delete(Long id) {
        return orderRepository.delete(id);
    }

    public Optional<Order> update(Long id, Order updatedOrder) {
        validateOrder(updatedOrder);
        return orderRepository.update(id, updatedOrder);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Customer name must not be empty");
        }

        if (order.getProductName() == null || order.getProductName().isBlank()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}