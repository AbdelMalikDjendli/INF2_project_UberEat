package fr.pantheonsorbonne.ufr27.miage.dto;

public class Menu {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String darkKitchen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDarkKitchen() {
        return darkKitchen;
    }

    public void setDarkKitchen(String darkKitchen) {
        this.darkKitchen = darkKitchen;
    }
}
