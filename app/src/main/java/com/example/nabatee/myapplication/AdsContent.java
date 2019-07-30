package com.example.nabatee.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdsContent extends AppCompatActivity {
    //Create Database reference for firebase
private DatabaseReference adsData;
//string for id of database item
private String id;
//Progress dialog for load data
public ProgressDialog LoadAD;
// create image view and group of text view to show data to user
private ImageView adsImage;
private TextView Name_AD,Location_AD,Description_AD,Price_AD,Phone_AD,Type_AD,Area_AD,Room_AD;
    //ad pictures info
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_content);
        //give reference for progress dialog and set title mesage content and make onclick cancel not true and show the progress
        LoadAD=new ProgressDialog(this);
        LoadAD.setTitle("Loading");
        LoadAD.setMessage("Please wait while loading info");
        LoadAD.setCanceledOnTouchOutside(false);
        LoadAD.show();
        //define intent to pass data between activity
        Intent adsintent=getIntent();
        id=adsintent.getStringExtra("Key");
        //Give reference for edit text and image view
        adsImage=findViewById(R.id.ImageADS);
        Name_AD=findViewById(R.id.Name_Home);
        Location_AD=findViewById(R.id.SakanLocation);
        Description_AD=findViewById(R.id.Description);
        Price_AD=findViewById(R.id.Price);
        Phone_AD=findViewById(R.id.PhoneNO);
        Type_AD=findViewById(R.id.Type);
        Area_AD=findViewById(R.id.Area);
        Room_AD=findViewById(R.id.Room);
        //give path for database and call method addvalue event listner to retrieve data from firebase database
        adsData=FirebaseDatabase.getInstance().getReference("ADS").child(id);
        adsData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //create object of Ads type and load data of it to text view and load image with picasso library by link
                Ads Currentads=dataSnapshot.getValue(Ads.class);
                Picasso.get().load(Currentads.ADimage).resize(375,250).centerCrop().into(adsImage);
                Name_AD.setText(Currentads.Name);
                Location_AD.setText(Currentads.Location);
                Price_AD.setText(Currentads.Price);
                Phone_AD.setText(Currentads.Phone_Number);
                Type_AD.setText(Currentads.Type);
                Area_AD.setText(Currentads.AreaX);
                Room_AD.setText(Currentads.Rooms);
                Description_AD.setText(Currentads.Descriptions);
                LoadAD.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //if no data available give message
                Toast.makeText(getApplicationContext(),"Error while loading Advertisement data",Toast.LENGTH_LONG).show();
            }
        });
        //Define Button to make user book the home and take him to payment page
        Button b1 =findViewById(R.id.book);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ADPayment=new Intent(AdsContent.this,Payment.class);
                ADPayment.putExtra("Key",id);
                startActivity(ADPayment);
            }
        });
    }
}
