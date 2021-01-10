package com.example.interestationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Priority;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    final String baseUrl = "https://interestation.azurewebsites.net/api/v1/";
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

        getUsers();
        getLikes();
        getPosts();

        postAdapter = new PostAdapter(this, R.layout.customcell, pList, requestQueue);
        post_listView.setAdapter(postAdapter);
    }

    private void getUsers() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl + "Users", null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

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
                postAdapter.notifyDataSetChanged();//To prevent app from crashing when updating
                //UI through background Thread
            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void getPosts() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl + "Posts", null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

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

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    Date date = simpleDateFormat.parse(dateString);

                    Post p = new Post(id, ownerId, ownerNick, ownerImg, text, date, image);
                    pList.add(0, p);

                    for (Like l: lList){
                        if(l.postId == id){
                            p.likes.add(l);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();//To prevent app from crashing when updating
                //UI through background Thread

            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void getLikes() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl + "Likes", null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

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
                postAdapter.notifyDataSetChanged();//To prevent app from crashing when updating
                //UI through background Thread
            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }
}