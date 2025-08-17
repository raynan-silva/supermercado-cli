package org.example.model;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;

    public Product() {} // necessário para Firestore

    public Product(String id, String name, String category, double price) {
        this.id = id; this.name = name; this.category = category; this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    @Override public String toString() {
        return String.format("[%s] %s (%s) - R$ %.2f", id, name, category, price);
    }
}

