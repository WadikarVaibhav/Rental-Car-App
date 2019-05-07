package com.android.carrental;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carrental.model.Car;
import com.android.carrental.model.CarBooking;
import com.android.carrental.model.CarModel;
import com.android.carrental.model.Station;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarBookingDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button confirm_booking;
    private TextView rate;
    private TextView car_name;
    private TextView car_color;
    private TextView date;
    private TextView start_time;
    private TextView end_time;
    private Station selectedStation;
    private Car selectedCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        confirm_booking = (Button) findViewById(R.id.btn_confirm_booking);
        rate = (TextView) findViewById(R.id.rate_for_booking);
        car_name = (TextView) findViewById(R.id.car_name_for_booking);
        car_color = (TextView) findViewById(R.id.car_color_for_booking);
        date = (TextView) findViewById(R.id.date_for_booking);
        start_time = (TextView) findViewById(R.id.start_time_for_booking);
        end_time = (TextView) findViewById(R.id.end_time_for_booking);
        confirm_booking.setOnClickListener(this);
        fetchSelectedCarDetails();
    }

    private void fetchSelectedCarDetails() {
        selectedCar = (Car) getIntent().getSerializableExtra("selectedCar");
        selectedStation = (Station) getIntent().getSerializableExtra("selectedStation");
        car_name.setText(selectedCar.getName());
        car_color.setText(selectedCar.getColor());
        date.setText(getIntent().getExtras().getString("selectedDate"));
        start_time.setText(getIntent().getExtras().getString("startTime"));
        end_time.setText(getIntent().getExtras().getString("endTime"));
        rate.setText(getIntent().getExtras().getInt("rate")+"");
    }

    @Override
    public void onClick(View v) {
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String startTime = getIntent().getExtras().getString("startTime");
        String endTime = getIntent().getExtras().getString("endTime");
        String date = getIntent().getExtras().getString("date");
        int rate = getIntent().getExtras().getInt("rate");
        CarBooking carBooking = new CarBooking(user, selectedCar, selectedStation, date, startTime, endTime, rate);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String id = databaseReference.push().getKey();
        databaseReference.child("bookings").child(id).setValue(carBooking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("saved", "saved");
                Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.child("users").child(user).child("bookings").child(id).setValue(carBooking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("saved", "saved");
                Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
