package com.example.interestationapp;

public class Like {
    public String id;
    public String userId;
    public int postId;

    Like(String id, String userId, int postId){
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }
}
