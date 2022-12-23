package com.example.fooddeliveryapp;
public class User {


    public String mUserName ,  email, password   , location  , type , phone ;



    public User() {

    }



    public User(String mUserName,  String email ,String password , String phone, String type ) {

        this.mUserName = mUserName;
        this.password = password;
        this.email = email;
        this.type = type;
        this.phone = phone;



    }




    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }



    public String getPassword() {
        return password;
    }



    public String getUserName() {
        return mUserName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
