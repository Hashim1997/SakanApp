package com.example.nabatee.myapplication;
//Create Class for ADS to create object from it
public class Ads {
    //create data field from String Location, Description,Area,Name,Type,Price,phone number,rooms,Image,Key id for database key id
    public String Location;
    public String Descriptions;
    public String AreaX;
    public String Name;
    public String Type;
    public String Rooms;
    public transient String keyID;
    public String ADimage;
    public String Price;
    public String Phone_Number;
    //Empty Constructor
    public Ads(){}
    //Constructor to assign data field member for object
    public Ads(String name,String location,String description,String area,String room,String price,String type,String adimage,String Number){
        this.Name=name;
        this.Location=location;
        this.Descriptions=description;
        this.AreaX=area;
        this.Rooms=room;
        this.Price=price;
        this.Type=type;
        this.ADimage=adimage;
        this.Phone_Number=Number;
    }
//create method setkeyid to pass key from firebase item to recyclerview
    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }
}
