package org.example.entity;

public class Product {
    public Product(int id, String productName, int price, int merchantId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.merchantId = merchantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    private int id;
    private String productName;
    private int price;
    private int merchantId;
}
