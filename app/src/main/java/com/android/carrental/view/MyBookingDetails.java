package com.android.carrental.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carrental.R;
import com.android.carrental.model.CarBooking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        if(carBooking.isComplete()){
            finish_booking.setVisibility(View.GONE);
            extend_booking.setVisibility(View.GONE);
        }
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
                finishTrip();
                break;
            case R.id.extend_trip:
        }
    }

    private void finishTrip() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarBooking booking = snapshot.getValue(CarBooking.class);
                    if (carBooking.getId().equals(booking.getId())) {
                        carBooking.setComplete(true);
                        databaseReference.child(booking.getId()).setValue(carBooking).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Thankyou", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), NearbyStations.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
