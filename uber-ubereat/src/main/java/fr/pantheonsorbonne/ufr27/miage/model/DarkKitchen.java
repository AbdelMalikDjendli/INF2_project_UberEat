package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;


@Entity
@Table(name = "dark_kitchens")
public class DarkKitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
