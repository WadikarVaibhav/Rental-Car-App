package com.android.carrental.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.carrental.R;

import java.util.Date;

public class CarBooking {

    private String user;
    private Station station;
    private String date;
    private String startTime;
    private String endTime;
    private Car car;
    private int rate;

    public CarBooking() {

    }

    public CarBooking(String user, Car car, Station station, String date, String startTime, String endTime, int rate) {
        this.user = user;
        this.station = station;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.car = car;
        this.rate = rate;
    }

    public Car getCar() {
        return car;
    }

    public String getUser() {
        return user;
    }

    public Station getStation() {
        return station;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
