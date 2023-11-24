package fr.pantheonsorbonne.ufr27.miage.model;

import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VenueLineUpId implements Serializable {
    private static final long serialVersionUID = -879274964975792491L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenueLineUpId that = (VenueLineUpId) o;
        return idVenue.equals(that.idVenue) && artist.equals(that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenue, artist);
    }

    @Column(name = "idVenue", nullable = false)
    private Integer idVenue;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Artist artist;


    public Integer getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(Integer idVenue) {
        this.idVenue = idVenue;
    }
}