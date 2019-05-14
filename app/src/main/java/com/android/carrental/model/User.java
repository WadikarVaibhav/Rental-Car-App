package com.android.carrental.model;

public class User {
    private final String name;
    private final String streetAddress;
    private final String aptNumber;
    private final String city;
    private final String zipCode;
    private final String email;
    private final String phoneNumber;
    private final String id;
    private CreditCard creditCard;

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard () {
        return this.creditCard;
    }

    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public User() {
        this.name = "";
        this.streetAddress = "";
        this.aptNumber = "";
        this.city = "";
        this.zipCode = "";
        this.email = "";
        this.id = "";
        this.phoneNumber = "";
        this.creditCard = null;
    }

    public User(String id, String cardId, String name, String email, String phoneNumber, String streetAddress, String aptNumber, String city, String zipCode) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.aptNumber = aptNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.creditCard = null;
        this.id = id;
    }
}