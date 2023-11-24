package fr.pantheonsorbonne.ufr27.miage.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Table(name = "Venue")
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenue", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Collection<VenueLineUp> getLineUp() {
        return lineUp;
    }

    public void setLineUp(Collection<VenueLineUp> lineUp) {
        this.lineUp = lineUp;
    }

    @OneToMany
    @JoinColumn(name = "idVenue")
    private Collection<VenueLineUp> lineUp;

    @ManyToOne
    @JoinColumn(name = "idLocation")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private LocalDate venueDate;

    public LocalDate getVenueDate() {
        return venueDate;
    }

    public void setVenueDate(LocalDate venueDate) {
        this.venueDate = venueDate;
    }
}