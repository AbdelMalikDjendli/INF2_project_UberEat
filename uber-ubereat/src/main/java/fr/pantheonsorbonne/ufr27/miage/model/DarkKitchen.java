package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dark_kitchens")
public class DarkKitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinColumn(name = "dark_kitchen_id")
    private List<Menu> menus; // Liste de menus associés à la dark kitchen



    // constructors, getters, and setters
}
