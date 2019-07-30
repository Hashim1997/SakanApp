package com.example.nabatee.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    public  EditText et_name ,et_email ,et_phone ,et_password ,et_cpassword;
    public   String name, email, phone, password, cpassword;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private StorageReference ProfileImage;
    public String  DownloadImageUrl=new String();
    public  boolean statee ;
   public boolean statues;
   public boolean statues2;
   SharedPreferences sigup;
    public ProgressDialog loading2;
    Uri imageURI;
   ImageView Profile2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loading2=new ProgressDialog(this);
        ProfileImage=FirebaseStorage.getInstance().getReference().child("Profile Image");
        Profile2=findViewById(R.id.addprofile);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        sigup=getSharedPreferences("Login", Context.MODE_PRIVATE);
        Profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallary();
            }
        });
        et_name = findViewById(R.id.input_name);
        et_email= findViewById(R.id.input_email);
        et_phone = findViewById(R.id.Phone_Number);
        et_password = findViewById(R.id.input_password) ;
        et_cpassword=findViewById(R.id.retype_password) ;
        final Switch simpleSwitch=findViewById(R.id.Simple_switch);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean switchstate=simpleSwitch.isChecked();
                if(switchstate==true)
                {
                    simpleSwitch.setText("STUDENT");

                }
                else
                {
                    simpleSwitch.setText("OWNER");
                }


            }
        });


        Button b1 =findViewById(R.id.btn_signup);



            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    name=et_name.getText().toString().trim();
                    email=et_email.getText().toString().trim();
                    phone=et_phone.getText().toString().trim();
                    password=et_password.getText().toString().trim();
                    cpassword=et_cpassword.getText().toString().trim();
                     statee = validate();
                     statues=verify1(phone);
                     statues2=verify2(phone);
                    if (statee == true ) {
                        Boolean  swtstate = simpleSwitch.isChecked();
                        if (swtstate == true && statues==false) {
                            loading2.setTitle("Add User");
                            loading2.setMessage("Please wait until finish Add your account");
                            loading2.setCanceledOnTouchOutside(false);
                            loading2.show();
                            //final StorageReference mountainsRef=ProfileImage.child(imageURI.getLastPathSegment()).child(FirebaseAuth.getInstance().getUid()+".png");
                            final StorageReference ref = ProfileImage.child("images/"+phone+".jpg");
                             UploadTask uploadTask = ref.putFile(imageURI);

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return ref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        DownloadImageUrl =downloadUri.toString();
                                        AddUser(DownloadImageUrl);
                                        loading2.dismiss();
                                        Toast.makeText(getApplicationContext(),"Uploading is Successful",Toast.LENGTH_LONG).show();
                                    } else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            });

                        }
                        else if (!statues2){
                            loading2.setTitle("Add User");
                            loading2.setMessage("Please wait until finish Add your account");
                            loading2.setCanceledOnTouchOutside(false);
                            loading2.show();
                            final StorageReference ref2 = ProfileImage.child("images/"+phone+".jpg");
                            UploadTask uploadTask = ref2.putFile(imageURI);

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return ref2.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        DownloadImageUrl =downloadUri.toString();
                                        AddOwner(DownloadImageUrl);
                                        loading2.dismiss();
                                        Toast.makeText(getApplicationContext(),"Uploading is Successful",Toast.LENGTH_LONG).show();
                                    } else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getApplicationContext(),"Uploading is failed",Toast.LENGTH_SHORT).show();
                                   loading2.dismiss();
                                }
                            });
                        }
                        else
                            Toast.makeText(getApplicationContext(),"You already have account",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Sign up failed",Toast.LENGTH_LONG).show();
                }
            });

        TextView L1 = findViewById(R.id.link_login);
        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(SignupActivity.this,Login.class);
                startActivity(i1);
            }
        });
    }

    public void AddUser(String urs){
        Student std=new Student(name,phone,password,email,urs);
        mDatabase.child("Students").child(phone).setValue(std);
        SharedPreferences.Editor mylogin=sigup.edit();
        mylogin.putString("data",std.PhoneNO);
        mylogin.putString("Type","Students");
        mylogin.commit();
        Intent i1 = new Intent(SignupActivity.this, ViewAds.class);
        startActivity(i1);
        finish();
    }
    public void AddOwner(String urs){
        Owner std=new Owner(name,phone,password,email,urs);
        mDatabase.child("Owner").child(phone).setValue(std);
        SharedPreferences.Editor mylogin=sigup.edit();
        mylogin.putString("data",std.PhoneNO);
        mylogin.putString("Type","Owner");
        mylogin.commit();
        Intent i1 = new Intent(SignupActivity.this, ViewAds.class);
        startActivity(i1);
        finish();
    }

public void opengallary(){
    Intent gallery =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);


}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap ProfileImage=null;
        if (resultCode==RESULT_OK && requestCode==100)
        {
            imageURI=data.getData();
            try {
                ProfileImage=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Profile2.setImageBitmap(ProfileImage);
        }
    }

    public boolean stats,stats2;
    public boolean verify1(String usr){
     mDatabase2=FirebaseDatabase.getInstance().getReference("Students").child(usr);

     mDatabase2.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             Student nam=dataSnapshot.getValue(Student.class);
             if (nam==null){
               stats=false;
             }
             else
               stats=true ;
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
     return stats;
    }

    public boolean verify2(String usr1){
        mDatabase3=FirebaseDatabase.getInstance().getReference("Owner").child(usr1);

        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Owner nam1=dataSnapshot.getValue(Owner.class);
                if (nam1==null){
                    stats2=false;
                }
                else
                    stats2=true ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return stats2;
    }


    public  Boolean validate(){

        boolean valid=true;
       if(name.isEmpty()){
        et_name.setError("please enter valid name");
        valid=false;

       }

        if (email.isEmpty()){

            et_email.setError("Please enter valid email address");
            valid=false;
        }

        if (password.isEmpty()){

            et_password.setError("Please enter valid email address");
            valid=false;
        }
        if (phone.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(phone).matches()){

            et_phone.setError("Please enter valid email address");
            valid=false;
        }
        if (cpassword.isEmpty()){

            et_cpassword.setError("Please enter valid email address");
            valid=false;
        }
        if(!password.equals(cpassword)){
           et_cpassword.setError("The Password Not Matches");
           et_password.setError("The PassWord Not matches");
           valid=false;
        }
        if (imageURI==null){
           Toast.makeText(getApplicationContext(),"Please Select Image for your Profile",Toast.LENGTH_SHORT).show();
           valid=false;
        }


        return  valid;
    }


     public  void intialize(){




     }
}
