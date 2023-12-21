package fr.pantheonsorbonne.ufr27.miage.dto;

import java.util.List;

public record OrderDto(
        Integer id,
        String status,
        Double lonCustomerPosition,
        Double latCustomerPosition,

        Double lonDarkKitchenPosition,
        Double latDarkKitchenPosition,

         List<MenuDto> menuItems
        ) {
        public int menu_id() {
                return 0;
        }

/*
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

 */
}
