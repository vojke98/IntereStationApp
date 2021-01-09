package com.example.interestationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView postlist;
    final String postsUrl = "https://interestation.azurewebsites.net/api/v1/Posts";
    final String usersUrl = "https://interestation.azurewebsites.net/api/v1/Users";
    List<Posts> plist;
    List<Users> ulist;
    PostAdapter adapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postlist = findViewById(R.id.post_list_view);
        plist = new ArrayList<>();
        ulist = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);//This will take care of background newtwork activities

        getUsers();
        getPosts();

        adapter = new PostAdapter(this, R.layout.customcell, plist);
        postlist.setAdapter(adapter);
    }

    private void getUsers() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, usersUrl, null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String firstName = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String userName = jsonObject.getString("userName");

                    ulist.add(new Users(id, firstName, lastName, userName));
                }
            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void getPosts() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, postsUrl, null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

                    String id = jsonObject.getString("postId");
                    String ownerId = jsonObject.getString("ownerId");
                    String dateString = jsonObject.getString("dateCreated");
                    String text = jsonObject.getString("text");
                    String image = jsonObject.getString("image");

                    String ownerNick = "username";

                    for (Users u : ulist) {
                        if (u.getId().equals(ownerId)) {
                            ownerNick = u.getUserName();
                        }
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS" );
                    Date date = simpleDateFormat.parse(dateString);

                    plist.add(new Posts(id, ownerNick, text, date, image));
                }
                adapter.notifyDataSetChanged();//To prevent app from crashing when updating
                //UI through background Thread
            } catch (Exception w) {
                Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }
}