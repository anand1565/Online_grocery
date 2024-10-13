package com.grocery.model;

public class Product {
    private String productId;       // Unique identifier for the product
    private String productName;     // Name of the product
    private double price;           // Price of the product
    private String description;     // Description of the product
    private String productImage;    // Path or URL of the product image

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(String productId, String productName, double price, String description, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
