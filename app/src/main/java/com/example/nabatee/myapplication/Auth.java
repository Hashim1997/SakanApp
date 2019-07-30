package com.example.nabatee.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Auth extends AppCompatActivity {
//create edit text,button ,string variable and progress dialog
    public EditText Number,Code;
    public Button Verify,Send;
    public String phoneNumber,phoneVerificationId;
    private FirebaseAuth fbAuth;
    public ProgressDialog loading;
    //create object of firebase authentication for force send code
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        fbAuth = FirebaseAuth.getInstance();
        Number = findViewById(R.id.Phone);
        Code = findViewById(R.id.Code);
        Verify = findViewById(R.id.BuVerify);
        Send = findViewById(R.id.BuSend);
        loading=new ProgressDialog(this);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //set title and message for loading and show it
                loading.setTitle("Phone Verification");
                loading.setMessage("Please wait until finish authenticate your phone number");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                //give text value of phone for edit text
                phoneNumber = Number.getText().toString().trim();
                //send phone number
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, Auth.this, verificationCallbacks);
            }
        });
        //Button verify to verify the sent code
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //progress dialog
                loading.setTitle("Code Verification");
                loading.setMessage("Please wait until finish Verify your code");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                //give text value of code for edit text
                String codeNo=Code.getText().toString().trim();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(phoneVerificationId,codeNo);
                signInWithPhoneAuthCredential(credential);
            }
        });


        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Verify.setEnabled(true);
                Send.setEnabled(false);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Please input correct phone number with country code",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                phoneVerificationId = s;
                resendingToken = forceResendingToken;
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Code has been sent",Toast.LENGTH_LONG).show();
            }
        };
    }
    // sign in phone auth if the number true give message
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(),"You are logged in successfully ",Toast.LENGTH_LONG).show();
                            Intent mainintent=new Intent(Auth.this,Login.class);
                            startActivity(mainintent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Sorry the number is wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
}
