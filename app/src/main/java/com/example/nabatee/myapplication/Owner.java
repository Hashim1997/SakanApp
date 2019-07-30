package com.example.nabatee.myapplication;

public class Owner extends User {
    public Ads ads;
    public Owner(){}
    public Owner(String name,String phone, String password,String email){
        NameX=name;
        Email=email;
        PhoneNO=phone;
        Password=password;
    }
    public Owner(String name,String phone, String password,String email,String url){
        NameX=name;
        Email=email;
        PhoneNO=phone;
        Password=password;
        image=url;
    }
    public Owner(String name,String phone, String password,String email,Ads ad){
        NameX=name;
        Email=email;
        PhoneNO=phone;
        Password=password;
        ads=ad;

    }
}
