package com.inventario.app.models;

import java.io.Serializable;

public class MonthlyClose implements Serializable {
    private String id;
    private String monthYear; // "01-2026"
    private long closeDate;
    private double totalSales;
    private double totalProfit;
    private int salesCount;
    private String closedBy;

    public MonthlyClose() {}

    public MonthlyClose(String monthYear, double totalSales, double totalProfit, int salesCount, String closedBy) {
        this.monthYear = monthYear;
        this.closeDate = System.currentTimeMillis();
        this.totalSales = totalSales;
        this.totalProfit = totalProfit;
        this.salesCount = salesCount;
        this.closedBy = closedBy;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public long getCloseDate() { return closeDate; }
    public void setCloseDate(long closeDate) { this.closeDate = closeDate; }
    public double getTotalSales() { return totalSales; }
    public void setTotalSales(double totalSales) { this.totalSales = totalSales; }
    public double getTotalProfit() { return totalProfit; }
    public void setTotalProfit(double totalProfit) { this.totalProfit = totalProfit; }
    public int getSalesCount() { return salesCount; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }
    public String getClosedBy() { return closedBy; }
    public void setClosedBy(String closedBy) { this.closedBy = closedBy; }
}
