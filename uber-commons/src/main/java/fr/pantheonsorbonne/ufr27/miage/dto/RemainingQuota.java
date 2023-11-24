package fr.pantheonsorbonne.ufr27.miage.dto;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RemainingQuota {

    private int standing;
    private int seating;

    public RemainingQuota() {
    }

    public RemainingQuota(int standing, int seating) {
        this.standing = standing;
        this.seating = seating;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }
}
