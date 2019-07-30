package com.example.nabatee.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//class for cenceling
public class Cancel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        //initiate button with click action that cancel the booking and return the user to home page
        final Button Cancel=(Button)findViewById(R.id.b2);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1= new Intent(Cancel.this,ViewAds.class);
                startActivity(I1);
            }
        });

}
}
