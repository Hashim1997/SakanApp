package com.example.nabatee.myapplication;
//Students class
public class User {
    public String NameX;
    public String PhoneNO;
    public String Password;
    public String Email;
    public String image;
    public User(){}
    public User(String name,String phone, String password,String email){
        this.Email=email;
        this.NameX=name;
        this.PhoneNO=phone;
        this.Password=password;
    }
    public User(String name,String phone, String password,String email,String url){
        this.Email=email;
        this.NameX=name;
        this.PhoneNO=phone;
        this.Password=password;
        this.image=url;
    }
}

