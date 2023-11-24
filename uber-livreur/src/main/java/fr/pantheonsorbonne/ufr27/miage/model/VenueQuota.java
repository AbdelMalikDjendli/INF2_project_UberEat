package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@NamedQueries(
        {
                @NamedQuery(name = "getQuotaForVendorForVenue", query = "SELECT v from VenueQuota v where v.id.vendor.id =:idVendor and v.id.venue.id =:idVenue"),
                @NamedQuery(name = "getAvailableVenuesForVendor", query = "SELECT v.id.venue from VenueQuota v where v.id.vendor.id = :idVendor and (v.seatingQuota>0 or v.standingQuota>0)")
        }
)
@Table(name = "VenueQuota")
@Entity
public class VenueQuota {
    @EmbeddedId
    private VenueQuotaId id;

    @Column(name = "seatingQuota", nullable = false)
    private Integer seatingQuota;

    @Column(name = "standingQuota", nullable = false)
    private Integer standingQuota;

    public Integer getStandingQuota() {
        return standingQuota;
    }

    public void setStandingQuota(Integer standingQuota) {
        this.standingQuota = standingQuota;
    }

    public Integer getSeatingQuota() {
        return seatingQuota;
    }

    public void setSeatingQuota(Integer seatingQuota) {
        this.seatingQuota = seatingQuota;
    }

    public VenueQuotaId getId() {
        return id;
    }

    public void setId(VenueQuotaId id) {
        this.id = id;
    }
}