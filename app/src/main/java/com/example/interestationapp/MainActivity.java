package com.example.interestationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String baseUrl = "https://web-interestation.azurewebsites.net/api/v1/";
    final int USERS = 0;
    final int LIKES = 1;
    final int POSTS = 2;

    ListView post_listView;
    List<Post> pList;
    List<User> uList;
    List<Like> lList;
    PostAdapter postAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        post_listView = findViewById(R.id.post_list_view);
        pList = new ArrayList<>();
        uList = new ArrayList<>();
        lList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);//This will take care of background newtwork activities

        fetchData(USERS);
        fetchData(LIKES);
        fetchData(POSTS);

        postAdapter = new PostAdapter(this, R.layout.customcell, pList, requestQueue);
        post_listView.setAdapter(postAdapter);
    }

    public  void fetchData(int object){
        String[] ext = new String[]{"Users", "Likes", "Posts"};

        JsonArrayRequest request = new JsonArrayRequest(baseUrl + ext[object], response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

                    if(object == 0){
                        unpackUsers(jsonObject);
                    }else if(object == 1) {
                        unpackLikes(jsonObject);
                    }else {
                        unpackPosts(jsonObject);
                    }
                }
                postAdapter.notifyDataSetChanged();//To prevent app from crashing when updating UI through background Thread
            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, errorListener){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("ApiKey", ApiKey.KEY);

                return params;
            }
        };
        requestQueue.add(request);
    }

    private final Response.ErrorListener errorListener = error -> Log.d("REST error", error.getMessage());

    public void unpackUsers(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.getString("id");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        String username = jsonObject.getString("userName");
        String image = jsonObject.getString("profilePic");

        uList.add(new User(id, firstName, lastName, username, image));

        for (Post p : pList) {
            if (id.equals(p.ownerId)) {
                p.ownerNick = username;
                p.ownerImg = image;
            }
        }
    }

    public void unpackLikes(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.getString("likeId");
        String userId = jsonObject.getString("userId");
        int postId = jsonObject.getInt("postId");

        Like l = new Like(id, userId, postId);
        lList.add(l);

        for(Post p: pList){
            if(p.id == postId){
                p.likes.add(l);
            }
        }
    }

    public void unpackPosts(JSONObject jsonObject) throws JSONException, ParseException {
        int id = jsonObject.getInt("postId");
        String ownerId = jsonObject.getString("ownerId");
        String dateString = jsonObject.getString("dateCreated");
        String text = jsonObject.getString("text");
        String image = jsonObject.getString("image");

        String ownerNick = "username";
        String ownerImg = null;

        for (User u : uList) {
            if (u.id.equals(ownerId)) {
                ownerNick = u.userName;
                ownerImg = u.image;
            }
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = simpleDateFormat.parse(dateString);

        Post p = new Post(id, ownerId, ownerNick, ownerImg, text, date, image);
        pList.add(0, p);

        for (Like l : lList) {
            if (l.postId == id) {
                p.likes.add(l);
            }
        }
    }
}