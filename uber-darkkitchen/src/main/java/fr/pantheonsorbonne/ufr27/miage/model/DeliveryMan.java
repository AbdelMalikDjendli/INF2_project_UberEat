package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

@Entity
@Table(name = "delivery_men")
public class DeliveryMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "vehicleType", nullable = false, length = 45)
    private String vehicleType;

    @Column(name = "latPosition", nullable = false, length = 45)
    private double latPosition;

    @Column(name = "lonPosition", nullable = false, length = 45)
    private double lonPosition;

    public DeliveryMan() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getLatPosition() {
        return latPosition;
    }

    public void setLatPosition(double latPosition) {
        this.latPosition = latPosition;
    }

    public double getLonPosition() {
        return lonPosition;
    }

    public void setLonPosition(double lonPosition) {
        this.lonPosition = lonPosition;
    }
}
