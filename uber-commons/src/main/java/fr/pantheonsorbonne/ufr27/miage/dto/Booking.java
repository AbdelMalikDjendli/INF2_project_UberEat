package fr.pantheonsorbonne.ufr27.miage.dto;

import java.util.ArrayList;
import java.util.Collection;

public class Booking {
    int vendorId;
    int venueId;
    int standingTicketsNumber;
    int seatingTicketsNumber;
    Collection<Integer> standingTransitionalTicker = new ArrayList<>();
    Collection<Integer> seatingTransitionalTicker = new ArrayList<>();

    public Collection<Integer> getStandingTransitionalTicket() {
        return standingTransitionalTicker;
    }

    public void setStandingTransitionalTicker(Collection<Integer> standingTransitionalTicker) {
        this.standingTransitionalTicker = standingTransitionalTicker;
    }

    public Collection<Integer> getSeatingTransitionalTicket() {
        return seatingTransitionalTicker;
    }

    public void setSeatingTransitionalTicker(Collection<Integer> seatingTransitionalTicker) {
        this.seatingTransitionalTicker = seatingTransitionalTicker;
    }

    public Booking(int vendorId, int venueId, int standingTicketsNumber, int seatingTicketsNumber) {
        this.vendorId = vendorId;
        this.venueId = venueId;
        this.standingTicketsNumber = standingTicketsNumber;
        this.seatingTicketsNumber = seatingTicketsNumber;
    }

    public Booking() {
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getStandingTicketsNumber() {
        return standingTicketsNumber;
    }

    public void setStandingTicketsNumber(int standingTicketsNumber) {
        this.standingTicketsNumber = standingTicketsNumber;
    }

    public int getSeatingTicketsNumber() {
        return seatingTicketsNumber;
    }

    public void setSeatingTicketsNumber(int seatingTicketsNumber) {
        this.seatingTicketsNumber = seatingTicketsNumber;
    }
}
