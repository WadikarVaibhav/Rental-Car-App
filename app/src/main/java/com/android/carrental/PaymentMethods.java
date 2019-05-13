package com.android.carrental;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.carrental.model.CreditCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class PaymentMethods extends AppCompatActivity implements View.OnClickListener  {
    Button save_card_details;
    EditText card_number;
    EditText editTextCvv;
    EditText zip_code;
    EditText exp_month;
    EditText exp_year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        save_card_details = (Button)findViewById(R.id.save_card_details);
        card_number = (EditText)findViewById(R.id.card_number);
        editTextCvv = (EditText)findViewById(R.id.cvv);
        zip_code = (EditText)findViewById(R.id.zip_code);
        save_card_details.setOnClickListener(this);
        getSupportActionBar().setTitle("Edit Payment Method");
        exp_month = (EditText)findViewById(R.id.exp_month);
        exp_year = (EditText)findViewById(R.id.exp_year);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_card_details : validateDetails();break;
        }
    }
    private void validateDetails(){
        String cardNumber = card_number.getText().toString().trim();
        String cvv = editTextCvv.getText().toString().trim();
        String zipCode = zip_code.getText().toString().trim();
        String monthString = exp_month.getText().toString().trim();
        String yearString = exp_year.getText().toString().trim();
        if (cardNumber.isEmpty()) {
            card_number.setError(getString(R.string.input_error));
            card_number.requestFocus();
            return;
        }
        if(monthString.isEmpty()){
            exp_month.setError(getString(R.string.input_error));
            exp_month.requestFocus();
            return;
        }
        if(yearString.isEmpty()){
            exp_year.setError(getString(R.string.input_error));
            exp_year.requestFocus();
            return;
        }
        if(monthString.length() !=2 && Integer.parseInt(monthString) <13 && Integer.parseInt(monthString) >0){
            exp_month.setError(getString(R.string.input_error_date_month));
            exp_month.requestFocus();
            return;
        }
        if(yearString.length() != 4 && Integer.parseInt(yearString) < 2019){
            exp_year.setError(getString(R.string.input_error_date_year));
            exp_year.requestFocus();
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

        CreditCard card = new CreditCard(cardNumber,cvv,zipCode,monthString,yearString);
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
