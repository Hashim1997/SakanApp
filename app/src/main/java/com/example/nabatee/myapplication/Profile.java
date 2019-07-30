package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private ImageView StudentImage,adsimages;
    private DatabaseReference StudentPage,adpage;
    private String type,Users;
    private SharedPreferences StudentPre;
    TextView StName,adname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        adsimages=findViewById(R.id.Myad);
        adname=findViewById(R.id.myadname);
        StudentPre=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Users=StudentPre.getString("data","Empty");
        type=StudentPre.getString("Type","Empty");
        StudentImage=findViewById(R.id.StudentImage);
        StName=findViewById(R.id.StudentName);
        StudentPage=FirebaseDatabase.getInstance().getReference(type).child(Users);
        StudentPage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student user2=dataSnapshot.getValue(Student.class);
                Picasso.get().load(user2.image).resize(100,100).centerCrop().into(StudentImage);
                StName.setText(user2.NameX);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error while Loading data please try again later",Toast.LENGTH_LONG).show();
            }
        });

       final LinearLayout lin=findViewById(R.id.lin2);
        adpage=FirebaseDatabase.getInstance().getReference("Booked home").child(Users);
        adpage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue().toString();
                if (value ==null){
                    lin.setVisibility(View.GONE);
                }
                else {
                    retreive(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(Profile.this,AdsContent.class);
                startActivity(i1);
            }
        });






    }
    public void retreive(String name){
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference("ADS").child(name);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Ads ads=dataSnapshot.getValue(Ads.class);
                adname.setText(ads.Name);
                Picasso.get().load(ads.ADimage).resize(100,100).centerCrop().into(adsimages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
