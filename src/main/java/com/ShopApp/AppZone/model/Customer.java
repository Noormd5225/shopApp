package com.ShopApp.AppZone.model;


import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    private String customerId;

    private String customerName;
    private String mobileNo;
    private String doorNo;
    private String streetName;
    private String townVillage;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private String email;
    private String password;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTownVillage() {
        return townVillage;
    }

    public void setTownVillage(String townVillage) {
        this.townVillage = townVillage;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(String customerId, String customerName, String mobileNo, String doorNo, String streetName, String townVillage, String district, String state, String country, String pincode, String email, String password) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.townVillage = townVillage;
        this.district = district;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.email = email;
        this.password = password;
    }


    public Customer(){}
// Getters and Setters
    // Constructor(s)
}

