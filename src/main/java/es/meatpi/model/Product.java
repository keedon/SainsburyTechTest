package es.meatpi.model;

public class Product {

    private final String title;
    private final Integer kcalPer100g;
    private final double unitPrice;
    private final String description;

    public Product(String title, Integer kcalPer100g, double unitPrice, String description) {
        this.title = title;
        this.kcalPer100g = kcalPer100g;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public Integer getKcalPer100g() {
        return kcalPer100g;
    }
}
