package fr.pantheonsorbonne.ufr27.miage.model;


import jakarta.persistence.*;

import java.time.Instant;

@Table(name = "Ticket", indexes = {
        @Index(name = "fk_Ticket_3_idx", columnList = "idCustomer"),
        @Index(name = "fk_Ticket_1_idx", columnList = "idVendor"),
        @Index(name = "fk_Ticket_2_idx", columnList = "idVenue")
})
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTicket", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idVenue", nullable = false)
    private Venue idVenue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idVendor", nullable = false)
    private Vendor idVendor;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idCustomer", nullable = true)
    private Customer idCustomer;

    public String getSeatReference() {
        return seatReference;
    }

    public void setSeatReference(String seatReference) {
        this.seatReference = seatReference;
    }

    @Column(nullable = true)
    private Instant validUntil;

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    @Column(nullable = true)
    private String seatReference;

    public String getTicketKey() {
        return ticketKey;
    }

    public void setTicketKey(String ticketKey) {
        this.ticketKey = ticketKey;
    }

    private String ticketKey;

    public Customer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Customer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Vendor getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(Vendor idVendor) {
        this.idVendor = idVendor;
    }

    public Venue getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(Venue idVenue) {
        this.idVenue = idVenue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}