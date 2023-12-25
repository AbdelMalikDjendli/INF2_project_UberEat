package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu; // Relation avec le menu

    @Column(name = "status")
    private String status = "recherche d'un restaurant";  // Statut de la commande


    @ManyToOne
    @JoinColumn(name = "dk_id", referencedColumnName = "id",nullable = true)
    private DarkKitchen darkKitchen;

    @ManyToOne
    @JoinColumn(name = "dm_id", referencedColumnName = "id",nullable = true)
    private DeliveryMan deliveryMan;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDarkKitchen(DarkKitchen darkKitchen) {
        this.darkKitchen = darkKitchen;
    }
}