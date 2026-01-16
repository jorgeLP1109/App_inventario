package com.inventario.app.models;

public class Product {
    private String id;
    private String name;
    private String barcode;
    private String categoryId;
    private double price;
    private double costPrice;
    private int stock;
    private int minStock;
    private String createdBy;
    private long createdAt;
    private long updatedAt;

    public Product() {}

    public Product(String name, String barcode, String categoryId, double price, double costPrice, int stock, int minStock, String createdBy) {
        this.name = name;
        this.barcode = barcode;
        this.categoryId = categoryId;
        this.price = price;
        this.costPrice = costPrice;
        this.stock = stock;
        this.minStock = minStock;
        this.createdBy = createdBy;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getCostPrice() { return costPrice; }
    public void setCostPrice(double costPrice) { this.costPrice = costPrice; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getMinStock() { return minStock; }
    public void setMinStock(int minStock) { this.minStock = minStock; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
