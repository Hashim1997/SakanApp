package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class OwnerpageActivity extends AppCompatActivity {

    private ImageView OwnerImage;
    private DatabaseReference OwnerPage;
    private String type,Users;
    private SharedPreferences OwnerPag;
    TextView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerpage);
        OwnerPag=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Users=OwnerPag.getString("data","Empty");
        type=OwnerPag.getString("Type","Empty");
        OwnerImage=findViewById(R.id.OwnerImage);
        Name=findViewById(R.id.Ownername);
        OwnerPage=FirebaseDatabase.getInstance().getReference(type).child(Users);
        OwnerPage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Owner user1=dataSnapshot.getValue(Owner.class);
                Picasso.get().load(user1.image).into(OwnerImage);
                Name.setText(user1.NameX);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error While Loading infor",Toast.LENGTH_LONG).show();
            }
        });
        final LinearLayout lay3=findViewById(R.id.layout1);
        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(OwnerpageActivity.this,AdsActivity.class);
                startActivity(i2);
            }
        });


    }
    //call menu OwnerMenu to show it in action bar that make the owner to post ads or menu to go to his account
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.ownermenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.home)
        {
            Intent profilei=new Intent(OwnerpageActivity.this,ViewAds.class);
            startActivity(profilei);
        }
        if (id==R.id.add)
        {
            Intent iadd=new Intent(OwnerpageActivity.this,PostAdActivity.class);
            startActivity(iadd);
        }
        if (id==R.id.settings)
        {
            Intent setting=new Intent(OwnerpageActivity.this,Edit_Profile.class);
            startActivity(setting);
        }
        return super.onOptionsItemSelected(item);
    }
}
