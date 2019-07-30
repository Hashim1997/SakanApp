package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Payment extends AppCompatActivity {
private String AD_id;
private DatabaseReference ADSref;
private SharedPreferences PaymentPre;
String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent pay=getIntent();
        PaymentPre=getSharedPreferences("Login", Context.MODE_PRIVATE);
        phone=PaymentPre.getString("data","Empty");
        AD_id=pay.getStringExtra("Key");
        ADSref=FirebaseDatabase.getInstance().getReference();
        Button Pay =findViewById(R.id.Pay);
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSref.child("Booked home").child(phone).setValue(AD_id);
                Toast.makeText(getApplicationContext(),"Successfully Payment",Toast.LENGTH_LONG).show();
                Intent Main= new Intent(Payment.this,ViewAds.class);
                startActivity(Main);
            }
        });
    }
}
