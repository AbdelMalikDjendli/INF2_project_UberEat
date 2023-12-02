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

    @OneToMany(mappedBy = "darkKitchen")
    private List<Menu> menus;

    // constructors, getters, and setters
}
