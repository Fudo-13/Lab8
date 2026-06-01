package ru.bsuedu.cad.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bsuedu.cad.lab.model.Order;
import ru.bsuedu.cad.lab.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("order", new Order());
        return "orders";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderService.create(order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "edit-order";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute Order order) {
        orderService.update(id, order);
        return "redirect:/orders";
    }
}