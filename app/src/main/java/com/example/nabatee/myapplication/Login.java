package com.example.nabatee.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Login extends AppCompatActivity {

   public SharedPreferences loginPref;
   public EditText name;
   public EditText password;
   public Button login;
   private String Num;
   private String Date;
   private EditText date;
    private DatabaseReference LoginDataBase;

    //login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        //create button that allow user to move to sign up to fill his info
        Button signup=(Button) findViewById(R.id.button3);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(Login.this,SignupActivity.class);
                startActivity(i2);
                finish();
            }
        });
        login=(Button)findViewById(R.id.button2);
        name =(EditText) findViewById(R.id.editText);
        password =(EditText) findViewById(R.id.editText2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login ( ){
        //define string to user name and password and check if the user already have account or not

        final String userN=name.getText().toString().trim();
         final String userPass=password.getText().toString().trim();
        LoginDataBase=FirebaseDatabase.getInstance().getReference("Students").child(userN);
        LoginDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student owner = dataSnapshot.getValue(Student.class);
                if (owner==null)
                {
                    login2();
                }
                 else if ((userPass.equals(owner.Password))) {
                    Toast.makeText(getApplicationContext(),"Welcome "+ owner.NameX, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor mylogin=loginPref.edit();
                    mylogin.putString("data",userN);
                    mylogin.putString("Type","Students");
                    mylogin.commit();
                        Intent i1 = new Intent(Login.this, ViewAds.class);
                        startActivity(i1);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong PassWord", Toast.LENGTH_LONG).show();
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_LONG).show();
                login2();
            }
        });
        }

        private void login2(){
        final String user1=name.getText().toString().trim();
        final String passwords=password.getText().toString().trim();
        LoginDataBase=FirebaseDatabase.getInstance().getReference("Owner").child(user1);
        LoginDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Owner usr=dataSnapshot.getValue(Owner.class);
                if (usr==null)
                {
                    Toast.makeText(getApplicationContext(),"User Name not available",Toast.LENGTH_LONG).show();
                }
                else if (passwords.equals(usr.Password))
                {
                    SharedPreferences.Editor mylogin=loginPref.edit();
                    mylogin.putString("data",user1);
                    mylogin.putString("Type","Owner");
                    mylogin.commit();
                    Toast.makeText(getApplicationContext(),"Welcome "+usr.NameX,Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Login.this, ViewAds.class);
                    startActivity(i2);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"User Name not available",Toast.LENGTH_LONG).show();

            }
        });
        }

    }


