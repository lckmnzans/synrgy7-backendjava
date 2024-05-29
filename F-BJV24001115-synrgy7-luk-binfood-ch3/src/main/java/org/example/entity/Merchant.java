package org.example.entity;

public class Merchant {
    public Merchant(int id, String merchantName, String merchantLocation, boolean open) {
        this.id = id;
        this.merchantName = merchantName;
        this.merchantLocation = merchantLocation;
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    private int id;
    private String merchantName;
    private String merchantLocation;
    private boolean open;
}
