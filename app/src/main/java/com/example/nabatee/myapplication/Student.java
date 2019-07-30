package com.example.nabatee.myapplication;

public class Student extends User {
    public String image;
    public Student(){}
    public Student(String name,String phone, String password,String email, String url){
        NameX=name;
        PhoneNO=phone;
        Password=password;
        Email=email;
        image=url;
    }
    public Student(String name,String phone, String password,String email){
        NameX=name;
        PhoneNO=phone;
        Password=password;
        Email=email;
    }
}
