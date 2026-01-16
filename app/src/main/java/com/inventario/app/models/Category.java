package com.inventario.app.models;

public class Category {
    private String id;
    private String name;
    private String color;
    private long createdAt;

    public Category() {}

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
        this.createdAt = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
