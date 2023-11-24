package fr.pantheonsorbonne.ufr27.miage.model;

import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ArtistTourId implements Serializable {
    private static final long serialVersionUID = 1018949744063089933L;
    @ManyToOne(cascade = CascadeType.ALL)
    private Artist artist;
    @ManyToOne(cascade = CascadeType.ALL)
    private Venue venue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistTourId that = (ArtistTourId) o;
        return artist.equals(that.artist) && venue.equals(that.venue);
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, venue);
    }
}