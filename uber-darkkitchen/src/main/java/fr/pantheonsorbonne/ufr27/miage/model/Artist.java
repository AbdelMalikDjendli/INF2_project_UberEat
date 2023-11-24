package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArtist", nullable = false)
    private Integer idArtist;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(Integer id) {
        this.idArtist = id;
    }
}