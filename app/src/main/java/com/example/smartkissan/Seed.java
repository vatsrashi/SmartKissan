package com.example.smartkissan;

public class Seed {
    private String name;
    private String description;
    private double price;
    private int imageResource; // Add this field for the image resource

    public Seed(String name, String description, double price, int imageResource) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }
}

