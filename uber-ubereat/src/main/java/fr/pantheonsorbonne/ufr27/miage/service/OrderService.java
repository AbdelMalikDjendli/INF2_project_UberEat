package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Order;

public interface OrderService {
    Order createOrder(fr.pantheonsorbonne.ufr27.miage.dto.Order order);
    Order getOrderById(Long orderId);
}
