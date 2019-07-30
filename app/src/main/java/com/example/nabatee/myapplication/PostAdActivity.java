package com.example.nabatee.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

public class PostAdActivity extends AppCompatActivity {

    public Spinner Location,AreaX,Rooms;
    private Button AddImage;
    public EditText Price,Descriptions,Name;
    public String location,areax,descriptions,name;
    private Bitmap adimage1=null;
    public String price,rooms;
    private DatabaseReference DataBase;
    private int Counter=0;
    private String types="Furnished";
    private StorageReference PostImage;
    private Uri image1;
    public ProgressDialog loading3;
    private ImageView ADIMAGE;
    private String pic,phone;
    SharedPreferences PostPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_ad);
        AddImage=findViewById(R.id.addimg);
        loading3=new ProgressDialog(this);
        Location=findViewById(R.id.Spin_location);
        AreaX=findViewById(R.id.Spin_Area);
        Rooms=findViewById(R.id.Spin_Room);
        ADIMAGE=findViewById(R.id.image);
        Name=findViewById(R.id.txtname);
        PostImage=FirebaseStorage.getInstance().getReference();
        Price=findViewById(R.id.Price);
        Descriptions=findViewById(R.id.Desc);
        PostPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        phone=PostPref.getString("data","Empty");
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        ADIMAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1=null;
                Counter=0;
                opengallery();
            }
        });
        final Switch simpleSwitch2;
        simpleSwitch2 = findViewById(R.id.switch2);
        final TextView text1 = findViewById(R.id.texta);
        simpleSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean switchstate = simpleSwitch2.isChecked();
                if (switchstate == true) {
                    text1.setText("Furnished");
                    types="Furnished";
                } else {
                    text1.setText("Unfernished");
                    types="Unfurnished";
                }
            }
        });

        Button Post=findViewById(R.id.btn_post);
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1!=null) {
                    loading3.setTitle("Post AD");
                    loading3.setMessage("Please wait until finish Post your AD");
                    loading3.setCanceledOnTouchOutside(false);
                    loading3.show();
                    ADD_Image();

                   // Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG);

                }
                else
                    Toast.makeText(getApplicationContext(),"Please Select Picture for your AD",Toast.LENGTH_SHORT).show();
                /*if (simpleSwitch2.isChecked())
                {
                    DataBase.child("ADS").push().setValue(ads);
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG);

                }
                else {
                    DataBase.child("ADS").push().setValue(ads);
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG);
                     }*/
                }
        });
    }
    public void opengallery(){
        Intent gallery =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Counter++;
        if (Counter==1)
        startActivityForResult(gallery, 100);
        else
            Toast.makeText(getApplicationContext(),"Sorry You Can't add more image",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==100){
            image1=data.getData();
            try {
                adimage1=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
            }
            catch (IOException E) {
                E.printStackTrace();
            }
            ADIMAGE.setImageBitmap(adimage1);
        }
        else
            Toast.makeText(getApplicationContext(),"Sorry You can't add more than one Picture in this demo",Toast.LENGTH_SHORT).show();
        /*
        else if (requestCode==RESULT_OK && requestCode==110){
            image2=data.getData();
            try {
                adimage2=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
            }
            catch (IOException C){
                C.printStackTrace();
            }
        }*/
    }
    void ADD_Image(){
         int randname;
        String ran;
        Random r=new Random();
        randname=r.nextInt(1+100)-100;
        ran=Integer.toString(randname);
        final StorageReference UploadImage=PostImage.child("ADS Image").child(phone+ran);
        UploadTask uploadTask=UploadImage.putFile(image1);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return UploadImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    pic =downloadUri.toString();
                    Post_ADD();
                    loading3.dismiss();
                    Toast.makeText(getApplicationContext(),"Uploading is Successful",Toast.LENGTH_LONG).show();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }
    void Post_ADD(){
        DataBase = FirebaseDatabase.getInstance().getReference();
        location = Location.getSelectedItem().toString();
        name=Name.getText().toString();
        areax = AreaX.getSelectedItem().toString();
        descriptions = Descriptions.getText().toString();
        price = Price.getText().toString();
        rooms = Rooms.getSelectedItem().toString();
        Ads ads = new Ads(name, location, descriptions, areax, rooms, price, types, pic,phone);
        DataBase.child("ADS").child(phone).setValue(ads);
        Intent home = new Intent(PostAdActivity.this, ViewAds.class);
        startActivity(home);
        finish();
    }
}
