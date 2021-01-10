package com.example.interestationapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {

    public int id;
    public String ownerId;
    public String text;
    public Date date;
    public String image;
    public String ownerNick;
    public String ownerImg;
    public List<Like> likes;

    Post(int id, String ownerId, String ownerNick, String ownerImg, String text, Date date, String image)
    {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.date = date;
        this.image = image;
        this.ownerNick = ownerNick;
        this.ownerImg = ownerImg;
        likes = new ArrayList<>();
    }
}