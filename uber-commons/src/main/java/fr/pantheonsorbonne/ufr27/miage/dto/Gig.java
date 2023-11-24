package fr.pantheonsorbonne.ufr27.miage.dto;


import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement
public class Gig {
    String artistName;
    String location;
    LocalDate date;
    int venueId;

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Gig(String artistName, String location, LocalDate date, int venueId) {
        this.artistName = artistName;
        this.location = location;
        this.date = date;
        this.venueId = venueId;
    }

    public Gig() {
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
