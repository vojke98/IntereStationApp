package com.example.interestationapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Posts> {
    final static String url = "https://interestation.azurewebsites.net/userFiles/939294f8-2e7f-4959-b896-7716d60e6faf/4/";
    Context context;
    int resource;
    List<Posts> postsList;
    PostAdapter(Context context ,int resource, List<Posts> postsList)
    {
        super(context,resource,postsList);
        this.context = context;
        this.resource = resource;
        this.postsList = postsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null,false);
        TextView owner = view.findViewById(R.id.owner_name_lbl);
        TextView date = view.findViewById(R.id.date_lbl);
        TextView text = view.findViewById(R.id.post_text_lbl);
        ImageView image = view.findViewById(R.id.post_imageview);
        Posts post = postsList.get(position);

        owner.setText(post.getOwner());
        date.setText(post.getDate().toString());
        text.setText(post.getText());

        String imageName = post.getImage();

        if(imageName.equals("null")){
            image.setVisibility(View.GONE);
        }else {
            Picasso.get().load(url + imageName).into(image);
        }

        return view;
    }
}
