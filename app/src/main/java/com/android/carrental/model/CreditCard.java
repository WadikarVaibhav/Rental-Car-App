package com.android.carrental.model;

public class CreditCard {
    String cardNumber;
    String cvv;
    String zipCode;
    String exp_month;
    String exp_year;

    public CreditCard(String cardNumber, String cvv, String zipCode, String exp_month, String exp_year) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.zipCode = zipCode;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
    }

    public CreditCard(){

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
