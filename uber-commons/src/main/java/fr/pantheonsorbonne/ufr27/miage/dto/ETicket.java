package fr.pantheonsorbonne.ufr27.miage.dto;

public class ETicket {
    TicketType type;
    double vendorFee;
    double fee;
    String email;
    String fname;
    String lname;
    int transitionalTicketId;

    public int getTransitionalTicketId() {
        return transitionalTicketId;
    }

    public void setTransitionalTicketId(int transitionalTicketId) {
        this.transitionalTicketId = transitionalTicketId;
    }

    public ETicket() {
    }

    public ETicket(TicketType type, double vendorFee, double fee, String email, String fname, String lname, int transitionalTicketId) {
        this.type = type;
        this.vendorFee = vendorFee;
        this.fee = fee;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.transitionalTicketId = transitionalTicketId;
    }

    public ETicket(int transitionalTicketId) {
        this.transitionalTicketId = transitionalTicketId;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public double getVendorFee() {
        return vendorFee;
    }

    public void setVendorFee(double vendorFee) {
        this.vendorFee = vendorFee;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
