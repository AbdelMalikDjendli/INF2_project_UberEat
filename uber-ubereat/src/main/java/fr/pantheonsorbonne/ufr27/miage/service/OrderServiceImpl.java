package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.MenuDto;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDto;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderServiceImpl implements OrderService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Order createOrder(OrderDto dtoOrderDto) {
        Log.info("Début de la création de la commande avec le menu ID: " + dtoOrderDto.menu_id());
        Order newOrder = new Order();
        newOrder.setId(Long.valueOf(dtoOrderDto.id()));
        if(!CollectionUtils.isEmpty(dtoOrderDto.menuItems())) {
            List<String> menuNames = dtoOrderDto.menuItems().stream().map(MenuDto::name).collect(Collectors.toList());
            // Récupérer et associer l'entité Menu à l'Order
            List<Menu> menus = entityManager.createQuery("SELECT m FROM Menu m WHERE m.name in :menuNames").setParameter("menuNames",menuNames).getResultList();
            menus.forEach(menu-> {
                newOrder.setMenu(menu);
            });
            // Le statut est déjà initialisé par défaut dans l'entité Order
            entityManager.persist(newOrder);
        }

        return newOrder;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return entityManager.find(Order.class, orderId);
    }
}
