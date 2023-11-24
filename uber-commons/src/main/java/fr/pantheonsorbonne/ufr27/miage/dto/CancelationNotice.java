package fr.pantheonsorbonne.ufr27.miage.dto;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CancelationNotice {
    String email;
    int ticketId;

    public CancelationNotice() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public CancelationNotice(String email, int ticketId) {
        this.email = email;
        this.ticketId = ticketId;
    }
}
