package com.android.carrental;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carrental.model.CreditCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.support.v7.app.AlertDialog.*;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MAX_YEAR = 2099;
    NumberPicker picker_month;
    NumberPicker picker_year;
    int exp_month;
    int exp_year;
    Button save_card_details;
    EditText card_number;
    EditText editTextCvv;
    EditText zip_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        picker_month = (NumberPicker)findViewById(R.id.picker_month);
        picker_year = (NumberPicker)findViewById(R.id.picker_year);
        save_card_details = (Button)findViewById(R.id.save_card_details);
        card_number = (EditText)findViewById(R.id.card_number);
        editTextCvv = (EditText)findViewById(R.id.cvv);
        zip_code = (EditText)findViewById(R.id.zip_code);
        save_card_details.setOnClickListener(this);

        picker_month.setMinValue(1);
        picker_month.setMaxValue(12);
        picker_year.setMinValue(2019);
        picker_year.setMaxValue(MAX_YEAR);

        picker_month.setWrapSelectorWheel(true);
        picker_year.setWrapSelectorWheel(true);
        picker_month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                exp_month = newVal;
            }
        });

        picker_year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                exp_year = newVal;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_card_details : validateDetails();
        }
    }

    private void validateDetails(){
        String cardNumber = card_number.getText().toString().trim();
        String cvv = editTextCvv.getText().toString().trim();
        String zipCode = zip_code.getText().toString().trim();
        if (cardNumber.isEmpty()) {
            card_number.setError(getString(R.string.input_error));
            card_number.requestFocus();
            return;
        }
        if (cvv.isEmpty()) {
            editTextCvv.setError(getString(R.string.input_error));
            editTextCvv.requestFocus();
            return;
        }
        if (zipCode.isEmpty()) {
            zip_code.setError(getString(R.string.input_error));
            zip_code.requestFocus();
            return;
        }
        CreditCard card = new CreditCard(cardNumber,cvv,zipCode,exp_month+"",exp_year+"");
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PaymentCards")
                .setValue(card).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
