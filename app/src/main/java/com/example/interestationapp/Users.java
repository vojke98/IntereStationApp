package com.example.interestationapp;

public class Users {

    public String firstName;
    public String lastName;
    public String userName;
    public String id;

    Users(String id, String firstName, String lastName, String userName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }
}
