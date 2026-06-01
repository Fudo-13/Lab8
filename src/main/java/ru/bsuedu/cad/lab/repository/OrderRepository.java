package ru.bsuedu.cad.lab.repository;

import org.springframework.stereotype.Repository;
import ru.bsuedu.cad.lab.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(3);

    public OrderRepository() {
        orders.add(new Order(1L, "Ivan Petrov", "Cat food", 2, 450.0));
        orders.add(new Order(2L, "Anna Smirnova", "Dog toy", 1, 320.0));
    }

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    public Order save(Order order) {
        order.setId(counter.getAndIncrement());
        orders.add(order);
        return order;
    }

    public boolean delete(Long id) {
        return orders.removeIf(order -> order.getId().equals(id));
    }

    public Optional<Order> update(Long id, Order updatedOrder) {
        return findById(id).map(order -> {
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setProductName(updatedOrder.getProductName());
            order.setQuantity(updatedOrder.getQuantity());
            order.setPrice(updatedOrder.getPrice());
            return order;
        });
    }

    public void clear() {
        orders.clear();
        counter.set(1);
    }
}