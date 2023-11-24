package fr.pantheonsorbonne.ufr27.miage.dto;

import java.util.Collection;

public class Cancellation {
    int venueId;
    Collection<String> customerEmails;

    public Cancellation(int venueId, Collection<String> customerEmails) {
        this.venueId = venueId;
        this.customerEmails = customerEmails;
    }

    public Cancellation() {
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public Collection<String> getCustomerEmails() {
        return customerEmails;
    }

    public void setCustomerEmails(Collection<String> customerEmails) {
        this.customerEmails = customerEmails;
    }
}
