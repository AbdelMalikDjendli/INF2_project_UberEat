package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Menu> menuItems;

    @Column(name = "status")
    private String status; // "pending", "in transit", "delivered"

    @Column(name = "delivery_address")
    private String deliveryAddress;

}
