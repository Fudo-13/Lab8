package ru.bsuedu.cad.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bsuedu.cad.lab.model.Order;
import ru.bsuedu.cad.lab.repository.OrderRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(new OrderRepository());
    }

    @Test
    void findAllShouldReturnInitialOrders() {
        assertEquals(2, orderService.findAll().size());
    }

    @Test
    void findByIdShouldReturnOrderWhenOrderExists() {
        Optional<Order> result = orderService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ivan Petrov", result.get().getCustomerName());
        assertEquals("Cat food", result.get().getProductName());
    }

    @Test
    void findByIdShouldReturnEmptyWhenOrderDoesNotExist() {
        Optional<Order> result = orderService.findById(100L);

        assertTrue(result.isEmpty());
    }

    @Test
    void createShouldAddOrderWhenDataIsCorrect() {
        Order order = new Order(null, "Petr Ivanov", "Parrot food", 3, 210.0);

        Order createdOrder = orderService.create(order);

        assertNotNull(createdOrder.getId());
        assertEquals("Petr Ivanov", createdOrder.getCustomerName());
        assertEquals("Parrot food", createdOrder.getProductName());
        assertEquals(3, createdOrder.getQuantity());
        assertEquals(210.0, createdOrder.getPrice());
        assertEquals(3, orderService.findAll().size());
    }

    @Test
    void createShouldThrowExceptionWhenCustomerNameIsEmpty() {
        Order order = new Order(null, "", "Cat food", 2, 450.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.create(order)
        );

        assertEquals("Customer name must not be empty", exception.getMessage());
    }

    @Test
    void createShouldThrowExceptionWhenQuantityIsNotPositive() {
        Order order = new Order(null, "Ivan Petrov", "Cat food", 0, 450.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.create(order)
        );

        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void createShouldThrowExceptionWhenPriceIsNotPositive() {
        Order order = new Order(null, "Ivan Petrov", "Cat food", 2, -100.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.create(order)
        );

        assertEquals("Price must be positive", exception.getMessage());
    }

    @Test
    void deleteShouldReturnTrueWhenOrderExists() {
        boolean result = orderService.delete(1L);

        assertTrue(result);
        assertEquals(1, orderService.findAll().size());
    }

    @Test
    void deleteShouldReturnFalseWhenOrderDoesNotExist() {
        boolean result = orderService.delete(100L);

        assertFalse(result);
        assertEquals(2, orderService.findAll().size());
    }

    @Test
    void updateShouldChangeOrderWhenOrderExists() {
        Order updatedOrder = new Order(null, "Sergey Orlov", "Dog food", 4, 800.0);

        Optional<Order> result = orderService.update(1L, updatedOrder);

        assertTrue(result.isPresent());
        assertEquals("Sergey Orlov", result.get().getCustomerName());
        assertEquals("Dog food", result.get().getProductName());
        assertEquals(4, result.get().getQuantity());
        assertEquals(800.0, result.get().getPrice());
    }

    @Test
    void updateShouldReturnEmptyWhenOrderDoesNotExist() {
        Order updatedOrder = new Order(null, "Sergey Orlov", "Dog food", 4, 800.0);

        Optional<Order> result = orderService.update(100L, updatedOrder);

        assertTrue(result.isEmpty());
    }
}