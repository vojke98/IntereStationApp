package com.example.interestationapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.UUID;

public class PostAdapter extends ArrayAdapter<Post> {
    final static String baseUrl = "https://interestation.azurewebsites.net/userFiles/";
    Context context;
    int resource;
    List<Post> postList;
    PostAdapter(Context context, int resource, List<Post> postList)
    {
        super(context,resource, postList);
        this.context = context;
        this.resource = resource;
        this.postList = postList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null,false);
        TextView owner = view.findViewById(R.id.owner_name_lbl);
        TextView date = view.findViewById(R.id.date_lbl);
        TextView text = view.findViewById(R.id.post_text_lbl);
        TextView likesCount = view.findViewById(R.id.likesCount);
        ImageView image = view.findViewById(R.id.post_imageview);
        ImageView ownerImage = view.findViewById(R.id.profilePic);

        ImageButton likeBtn = view.findViewById(R.id.likeBtn);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID testGuid = UUID.randomUUID(); //generate random GUID
                //send POST request (create new like)
            }
        });

        Post post = postList.get(position);

        owner.setText(post.ownerNick);
        date.setText(post.date.toString());
        text.setText(post.text);
        likesCount.setText(post.likes.size() + "");

        if(post.image.equals("null")){
            image.setVisibility(View.GONE);
        }else {
            String url = baseUrl + post.ownerId + "/" + post.id + "/" + post.image;
            Picasso.get().load(url).into(image);
            url = baseUrl + post.ownerId + "/" + post.ownerImg;
            Picasso.get().load(url).into(ownerImage);
        }

        return view;
    }
}
