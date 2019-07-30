package com.example.nabatee.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

//java file refer to account activity
public class AdsActivity extends AppCompatActivity {
//To Delete or edit Ads
    public Button Delete;
    public Button Editad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        Delete=findViewById(R.id.delete_ad);
        Editad=findViewById(R.id.edit_ad);
    }
}
