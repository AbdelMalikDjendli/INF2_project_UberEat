package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Table(name = "VenueLineUp")
@Entity
public class VenueLineUp {
    @EmbeddedId
    private VenueLineUpId id;

    @Column(name = "showTime", length = 45)
    private String showTime;

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public VenueLineUpId getId() {
        return id;
    }

    public void setId(VenueLineUpId id) {
        this.id = id;
    }
}