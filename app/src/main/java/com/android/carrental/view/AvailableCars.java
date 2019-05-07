package com.android.carrental.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.carrental.R;
import com.android.carrental.adapter.AvailableCarsAdapter;
import com.android.carrental.adapter.StationAdapter;
import com.android.carrental.model.Car;
import com.android.carrental.model.CarBooking;
import com.android.carrental.model.CarModel;
import com.android.carrental.model.Station;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AvailableCars extends AppCompatActivity {

    private RecyclerView available_cars_recylerview;
    private List<Car> availableCars;
    private AvailableCarsAdapter availableCarsAdapter;
    private List<Car> allCars;
    private List<CarBooking> allCarBookings;
    private Station selectedStation;
    private CarModel selectedCarModel;
    private String selectedDate;
    private String selectedStartTime;
    private String selectedEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cars);
        available_cars_recylerview = findViewById(R.id.recycler_view_available_cars);
        allCars = new ArrayList<>();
        allCarBookings = new ArrayList<>();
        availableCars = new ArrayList<>();
        getUserSelectedData();
        initWidgets();
    }

    private void getUserSelectedData() {
        selectedStation = (Station) getIntent().getSerializableExtra("selectedStaion");
        selectedCarModel = (CarModel) getIntent().getSerializableExtra("selectedCarModel");
        try {
//            selectedDate = new SimpleDateFormat("dd MMM yyyy").parse(getIntent().getStringExtra("selectedDate"));
            selectedDate = getIntent().getStringExtra("selectedDate");
            selectedStartTime = getIntent().getStringExtra("selectedStartTime");
            selectedEndTime = getIntent().getStringExtra("selectedEndTime");
        } catch (Exception e) {

        }
    }

    private void initWidgets() {
        available_cars_recylerview.setHasFixedSize(true);
        available_cars_recylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        allCars = getAllCars();
        allCarBookings = getAllCarBookings();
        availableCars = new ArrayList<>();
        availableCarsAdapter = new AvailableCarsAdapter(getApplicationContext(), availableCars, this, selectedStation, selectedDate, selectedStartTime, selectedEndTime);
        availableCarsAdapter.notifyDataSetChanged();
        available_cars_recylerview.setAdapter(availableCarsAdapter);
    }

    private List<Car> getAllCars() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cars");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Car car = snapshot.getValue(Car.class);
                    if (car.getStation().getId().equals(selectedStation.getId())) {
                        if (selectedCarModel.getName().equals(car.getModel().getName())) {
                            allCars.add(car);
                        } else if (selectedCarModel.getName().equals("All Types")) {
                            allCars.add(car);
                        }
                    }
                }
                availableCars = getAvailableCars();
                availableCarsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allCars;
    }

    private List<CarBooking> getAllCarBookings() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarBooking carBooking = snapshot.getValue(CarBooking.class);
                    allCarBookings.add(carBooking);
                }
                availableCarsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allCarBookings;
    }

    private List<Car> getAvailableCars() {
        for (Car car : allCars) {
            boolean isCarBooked = false;
            for (CarBooking booking : allCarBookings) {
                if (booking.getDate().equals(selectedDate) && isTimeConflict(booking.getStartTime(), booking.getEndTime())) {
                    isCarBooked = true;
                }
            }
            if (!isCarBooked) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    private boolean isTimeConflict(String startTime, String endTime) {
        return false;
    }
}
