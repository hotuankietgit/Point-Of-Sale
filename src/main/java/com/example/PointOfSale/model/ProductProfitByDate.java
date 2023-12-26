package com.example.PointOfSale.model;

public class ProductProfitByDate {
    private String date;
    private double totalProfit;

    public ProductProfitByDate(String date, double totalProfit) {
        this.date = date;
        this.totalProfit = totalProfit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
