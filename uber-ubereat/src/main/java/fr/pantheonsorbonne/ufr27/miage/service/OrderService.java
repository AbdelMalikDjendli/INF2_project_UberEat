package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.OrderDto;
import fr.pantheonsorbonne.ufr27.miage.model.Order;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order getOrderById(Long orderId);
}
