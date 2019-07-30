package com.example.nabatee.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAds extends AppCompatActivity {

   public SharedPreferences loginPref;
   private String keyvalue;
    private List<Ads> list=new ArrayList<>();
    private MyAdapter myAdapter;
   private DatabaseReference Adsretreive;
   public Button search;
   public EditText search_word;
   public ProgressDialog ADload;
    //ads page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewads);
        ADload=new ProgressDialog(this);
        ADload.setTitle("Loading");
        ADload.setMessage("Please wait");
        ADload.setCanceledOnTouchOutside(false);
        ADload.show();
        search=findViewById(R.id.searchbtn);
        search_word=findViewById(R.id.searchbar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_data(search_word.getText().toString());
            }
        });
        loginPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Adsretreive=FirebaseDatabase.getInstance().getReference("ADS");
       final RecyclerView recyclerView=findViewById(R.id.ViewADS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adsretreive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Ads ads=dataSnapshot1.getValue(Ads.class);
                    list.add(ads);
                    keyvalue=dataSnapshot1.getKey();
                    ads.setKeyID(keyvalue);
                }
                 myAdapter=new MyAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ADload.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed While loading data",Toast.LENGTH_SHORT);
            }
        });

    }
    public void search_data(final String text){
        list.clear();
        final String []rad =text.split(" ");
        loginPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Adsretreive=FirebaseDatabase.getInstance().getReference("ADS");
        final RecyclerView recyclerView=findViewById(R.id.ViewADS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adsretreive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Ads ads=dataSnapshot1.getValue(Ads.class);
                    for (String temp:rad){
                    if (ads.Name.contains(temp)){
                    list.add(ads);
                    keyvalue=dataSnapshot1.getKey();
                    ads.setKeyID(keyvalue);
                    break;
                }
                else if (ads.Phone_Number.contains(temp)){
                        list.add(ads);
                        keyvalue=dataSnapshot1.getKey();
                        ads.setKeyID(keyvalue);
                        break;
                    }
                    else if (ads.Location.contains(temp)){
                        list.add(ads);
                        keyvalue=dataSnapshot1.getKey();
                        ads.setKeyID(keyvalue);
                        break;
                    }
                    }
                }
                if (!list.equals(null)) {
                    myAdapter = new MyAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    ADload.dismiss();
                }
                else
                    Toast.makeText(getApplicationContext(),"No result match your search",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed While loading data",Toast.LENGTH_SHORT);
            }
        });

    }

    public void Logout(){
        SharedPreferences.Editor logout=loginPref.edit();
        logout.clear();
        logout.commit();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.profiles)
        {
            SharedPreferences.Editor profile=loginPref.edit();
            String types=loginPref.getString("Type","not");
            if (types=="Students") {
                Intent profilei = new Intent(ViewAds.this, Profile.class);
                startActivity(profilei);
            }
            else {
                Intent own=new Intent(ViewAds.this,OwnerpageActivity.class);
                startActivity(own);
            }
        }
        if (id==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Logout();
            Intent logins=new Intent(ViewAds.this,Auth.class);
            startActivity(logins);
        }
        if(id==R.id.settings){
            Intent Edit=new Intent(ViewAds.this,Edit_Profile.class);
            startActivity(Edit);
        }
        return super.onOptionsItemSelected(item);

    }

}
