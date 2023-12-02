package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

@Entity
@Table(name = "delivery_men")
public class DeliveryMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "gps_location")
    private String gpsLocation;

    // constructors, getters, and setters
}
