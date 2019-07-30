package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Edit_Profile extends AppCompatActivity {
//create edit text string button image database refernec to retreive data and edit it
    public EditText editname ,editnumber ,editpassword ,editemail;
    public String ename, enumber, epassword, eemail,Users,type;
    public Button Submit,Delete;
    public ImageView pro;
    private DatabaseReference editDatabase;
    SharedPreferences EditPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        //shared prefernec to retreive stored data about phone number of user
        EditPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Users=EditPref.getString("data","Empty");
        type=EditPref.getString("Type","Empty");
        editname=findViewById(R.id.edit_name);
        editemail=findViewById(R.id.edit_email);
        editnumber=findViewById(R.id.edit_no);
        editpassword=findViewById(R.id.new_pass);
        Submit=findViewById(R.id.btn_edit);
        Delete=findViewById(R.id.dele);
        pro=findViewById(R.id.pro);
//call method retrieve to retrieve data
        retrieve();

//button to submit new value
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ename=editname.getText().toString().trim();
                enumber=editnumber.getText().toString().trim();
                eemail=editemail.getText().toString().trim();
                epassword=editpassword.getText().toString().trim();
                User userx=new User(ename, enumber, epassword, eemail);
                editDatabase.setValue(userx);
            }
        });
//button to delete account
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDatabase=FirebaseDatabase.getInstance().getReference(type);
                editDatabase.child(Users).removeValue();
                SharedPreferences.Editor logout=EditPref.edit();
                logout.clear();
                Runtime.getRuntime().gc();
                logout.commit();
                Intent log= new Intent(Edit_Profile.this,Login.class);
                startActivity(log);
                finish();
            }
        });

    }
    //method to retrieve data from firebase
    public void retrieve(){
        editDatabase=FirebaseDatabase.getInstance().getReference(type).child(Users);
        editDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User euser=dataSnapshot.getValue(User.class);
                if (euser==null){

                }
                else {
                    Picasso.get().load(euser.image).into(pro);
                    editname.setText(euser.NameX);
                    editemail.setText(euser.Email);
                    editnumber.setText(euser.PhoneNO);
                    editpassword.setText(euser.Password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG);
            }
        });
    }
}
