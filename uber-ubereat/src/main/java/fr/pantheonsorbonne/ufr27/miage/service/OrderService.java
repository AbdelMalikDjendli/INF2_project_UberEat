package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    OrderDTO getOrderDTOFromModel(long orderId);
    Order dkFoundUpdate(String dkName);

    Order deliveryManUpdate(String dmName) ;
    void noneDeliveryManUpdate();


}
