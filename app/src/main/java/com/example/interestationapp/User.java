package com.example.interestationapp;

public class User {

    public String firstName;
    public String lastName;
    public String userName;
    public String image;
    public String id;

    User(String id, String firstName, String lastName, String userName, String image){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.image = image;
    }
}
