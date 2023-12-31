package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DeliveryManDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    MenuDAO menuDAO;

    @Inject
    OrderDAO orderDAO;

    @Inject
    DkDAO dkDAO;

    @Inject
    DeliveryManDAO deliveryManDAO;

    @Override
    @Transactional
    public Order createOrder(OrderDTO dtoOrder) {
        Order newOrder = new Order();
        // Récupérer et associer l'entité Menu à l'Order
        //Menu menu = entityManager.find(Menu.class, (long) dtoOrder.menu_id());
        Menu menu = menuDAO.findMenuByName(dtoOrder.menu().name());
        newOrder.setMenu(menu);
        newOrder.setStatus("En recherche de restaurant");
        entityManager.persist(newOrder);
        return newOrder;
    }

    /*
        Convertit un model Order en OrderDTO
     */
    @Override
    @Transactional
    public OrderDTO getOrderDTOFromModel(long orderId) {
        Order orderModel = orderDAO.findOrderById(orderId);
        MenuDTO menuDto = new MenuDTO(orderModel.getMenu().getName(), orderModel.getMenu().getDescription());
        return new OrderDTO( orderModel.getStatus(), menuDto, orderModel.getName());
    }

    /*
    Met à jour le status et la darkkitchen d'une commande une fois que la darkkitchen a été choisie
    et retourne la commande
     */
    @Override
    @Transactional
    public Order dkFoundUpdate(String dkName) {
        Order orderModel = orderDAO.getLastOrder();
        orderDAO.updateStatus(orderModel.getId(), "en cours de préparation");
        orderDAO.addDarkKitchen(orderModel.getId(), dkDAO.findDKByName(dkName));
        return orderModel;
    }


    @Override
    @Transactional
    public Order deliveryManUpdate(String dmName) {
        Order orderModel = orderDAO.getLastOrder();
        orderDAO.updateStatus(orderModel.getId(), "livreur trouvé");
        orderDAO.addDeliveryMan(orderModel.getId(), deliveryManDAO.findDMByName(dmName));
        deliveryManDAO.setIsAvaible(dmName,false);
        return orderModel;
    }

    @Override
    @Transactional
    public void noneDeliveryManUpdate(Long oderId) {
        orderDAO.findOrderById(oderId);
        orderDAO.updateStatus(oderId, "aucun livreur trouvé");

    }

    @Override
    public int countTotalDeliveryMen() {
        return deliveryManDAO.countTotalDeliveryMen();
    }

}
