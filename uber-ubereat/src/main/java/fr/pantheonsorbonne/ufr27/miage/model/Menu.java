package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
