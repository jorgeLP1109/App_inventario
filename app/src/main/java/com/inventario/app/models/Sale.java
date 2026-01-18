package com.inventario.app.models;

import java.io.Serializable;
import java.util.List;

public class Sale implements Serializable {
    private String id;
    private List<SaleItem> items;
    private double total;
    private double profit;
    private String soldBy;
    private long timestamp;
    private String paymentType; // "CONTADO" o "CREDITO" o "NEKI"
    private String customerName;
    private boolean isPaid;
    private String nekiReference; // Código de referencia para pagos Neki

    public Sale() {}

    public Sale(List<SaleItem> items, double total, double profit, String soldBy, String paymentType, String customerName, String nekiReference) {
        this.items = items;
        this.total = total;
        this.profit = profit;
        this.soldBy = soldBy;
        this.timestamp = System.currentTimeMillis();
        this.paymentType = paymentType;
        this.customerName = customerName;
        this.nekiReference = nekiReference;
        this.isPaid = "CONTADO".equals(paymentType) || "NEKI".equals(paymentType);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public double getProfit() { return profit; }
    public void setProfit(double profit) { this.profit = profit; }
    public String getSoldBy() { return soldBy; }
    public void setSoldBy(String soldBy) { this.soldBy = soldBy; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
    public String getNekiReference() { return nekiReference; }
    public void setNekiReference(String nekiReference) { this.nekiReference = nekiReference; }

    public static class SaleItem implements Serializable {
        private String productId;
        private String productName;
        private int quantity;
        private double price;
        private double costPrice;

        public SaleItem() {}

        public SaleItem(String productId, String productName, int quantity, double price, double costPrice) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.costPrice = costPrice;
        }

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public double getCostPrice() { return costPrice; }
        public void setCostPrice(double costPrice) { this.costPrice = costPrice; }
    }
}
