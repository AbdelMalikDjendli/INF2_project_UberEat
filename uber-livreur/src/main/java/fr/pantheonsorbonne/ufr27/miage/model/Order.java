package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dk_id", referencedColumnName = "id")
    private DarkKitchen dk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DarkKitchen getDk() {
        return dk;
    }

    public void setDk(DarkKitchen dk) {
        this.dk = dk;
    }

}