package com.example.interestationapp;

import android.media.Image;

import java.util.Date;

public class Posts {

    public String id;
    public String ownerId;
    public String text;
    public Date date;
    public String image;
    public String ownerName;

    Posts(String id, String ownerId, String text, Date date, String image)
    {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.date = date;
        this.image = image;
    }

    public String getOwner() {
        return ownerId;
    }

    public void setOwner(String owner) {
        this.ownerId = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
