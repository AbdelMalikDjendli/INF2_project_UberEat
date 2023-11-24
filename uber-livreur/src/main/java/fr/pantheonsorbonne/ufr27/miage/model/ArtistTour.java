package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;


@Entity
public class ArtistTour {
    @EmbeddedId
    private ArtistTourId id;

    public ArtistTourId getId() {
        return id;
    }

    public void setId(ArtistTourId id) {
        this.id = id;
    }
}