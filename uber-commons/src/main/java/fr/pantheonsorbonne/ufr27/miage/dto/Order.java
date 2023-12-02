package fr.pantheonsorbonne.ufr27.miage.dto;

public class Order {
    int id;
    String status;
    double lonCustomerPosition;
    double latCustomerPosition;

    double lonDarkKitchenPosition;
    double latDarkKitchenPosition;


    public Order(int id, String status, double lonCustomerPosition, double latCustomerPosition, double lonDarkKitchenPosition, double latDarkKitchenPosition) {
        this.id = id;
        this.status = status;
        this.lonCustomerPosition = lonCustomerPosition;
        this.latCustomerPosition = latCustomerPosition;
        this.lonDarkKitchenPosition = lonDarkKitchenPosition;
        this.latDarkKitchenPosition = latDarkKitchenPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLonCustomerPosition() {
        return lonCustomerPosition;
    }

    public void setLonCustomerPosition(double lonCustomerPosition) {
        this.lonCustomerPosition = lonCustomerPosition;
    }

    public double getLatCustomerPosition() {
        return latCustomerPosition;
    }

    public void setLatCustomerPosition(double latCustomerPosition) {
        this.latCustomerPosition = latCustomerPosition;
    }

    public double getLonDarkKitchenPosition() {
        return lonDarkKitchenPosition;
    }

    public void setLonDarkKitchenPosition(double lonDarkKitchenPosition) {
        this.lonDarkKitchenPosition = lonDarkKitchenPosition;
    }

    public double getLatDarkKitchenPosition() {
        return latDarkKitchenPosition;
    }

    public void setLatDarkKitchenPosition(double latDarkKitchenPosition) {
        this.latDarkKitchenPosition = latDarkKitchenPosition;
    }

    public double getDistanceBetweenCustomerAndDarkKitchen(double lat1,double lon1,double lat2,double lon2){
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371000 * c;
        //metre
    }
}
