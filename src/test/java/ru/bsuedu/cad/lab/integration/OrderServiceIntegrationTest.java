package ru.bsuedu.cad.lab.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bsuedu.cad.lab.model.Order;
import ru.bsuedu.cad.lab.repository.OrderRepository;
import ru.bsuedu.cad.lab.service.OrderService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceIntegrationTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orderRepository.clear();
        orderService = new OrderService(orderRepository);
    }

    @Test
    void createShouldSaveOrderThroughRepository() {
        Order order = new Order(null, "Maria Volkova", "Fish food", 2, 180.0);

        Order createdOrder = orderService.create(order);

        assertNotNull(createdOrder.getId());
        assertEquals(1, orderRepository.findAll().size());
        assertEquals("Maria Volkova", orderRepository.findAll().get(0).getCustomerName());
        assertEquals("Fish food", orderRepository.findAll().get(0).getProductName());
    }

    @Test
    void createShouldNotSaveOrderWhenQuantityIsInvalid() {
        Order order = new Order(null, "Maria Volkova", "Fish food", 0, 180.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.create(order)
        );

        assertEquals("Quantity must be positive", exception.getMessage());
        assertTrue(orderRepository.findAll().isEmpty());
    }

    @Test
    void findByIdShouldReturnOrderSavedByService() {
        Order order = new Order(null, "Oleg Sokolov", "Hamster cage", 1, 950.0);

        Order createdOrder = orderService.create(order);
        Optional<Order> foundOrder = orderService.findById(createdOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals("Oleg Sokolov", foundOrder.get().getCustomerName());
        assertEquals("Hamster cage", foundOrder.get().getProductName());
    }

    @Test
    void deleteShouldRemoveOrderFromRepository() {
        Order order = new Order(null, "Anna Petrova", "Bird food", 1, 260.0);

        Order createdOrder = orderService.create(order);
        boolean deleted = orderService.delete(createdOrder.getId());

        assertTrue(deleted);
        assertTrue(orderRepository.findAll().isEmpty());
    }
}