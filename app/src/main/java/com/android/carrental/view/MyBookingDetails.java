package com.android.carrental.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carrental.R;
import com.android.carrental.model.CarBooking;

public class MyBookingDetails extends AppCompatActivity implements View.OnClickListener {

    private ImageView car_model_image;
    private TextView car_name;
    private TextView pickup_location;
    private TextView start_time;
    private TextView end_time;
    private TextView date;
    private TextView total_fare;
    private Button finish_booking;
    private Button extend_booking;
    CarBooking carBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_details);
//        car_model_image = (ImageView) findViewById(R.id.car_model_image);
        car_name = (TextView) findViewById(R.id.car_name_booking_details);
        pickup_location = (TextView) findViewById(R.id.pickup_location_on_booking_details);
        start_time = (TextView) findViewById(R.id.start_time_on_booking_details);
        end_time = (TextView) findViewById(R.id.end_time_on_booking_details);
        date = (TextView) findViewById(R.id.date_on_booking_details);
        total_fare = (TextView) findViewById(R.id.rate_on_booking_details);
        finish_booking = (Button) findViewById(R.id.finish_trip);
        extend_booking = (Button) findViewById(R.id.extend_trip);
        finish_booking.setOnClickListener(this);
        extend_booking.setOnClickListener(this);
        getSupportActionBar().setTitle("Booking Details");
        carBooking = (CarBooking) getIntent().getSerializableExtra("booking");
        fetchDetailsForMyBooking();

    }

    private void fetchDetailsForMyBooking() {
        car_name.setText(carBooking.getCar().getName());
        pickup_location.setText(carBooking.getStation().getAddress());
        start_time.setText(carBooking.getStartTime());
        end_time.setText(carBooking.getEndTime());
        total_fare.setText(carBooking.getRate() + "");
        date.setText(carBooking.getBookingDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_trip:
                carBooking.setTripStatus(true);
                Toast.makeText(getApplicationContext(), "Trip finished", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, NearbyStations.class));
                finish();
                break;
            case R.id.extend_trip:
        }

    }
}
