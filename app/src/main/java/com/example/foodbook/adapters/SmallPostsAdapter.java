package com.example.foodbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import interfaces.ItemClickInterface;


public class SmallPostsAdapter extends RecyclerView.Adapter<SmallPostsAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDish;
        private final TextView tvLikesNum;

        private PostViewHolder(View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            tvDish = itemView.findViewById(R.id.tvDish);
            tvLikesNum = itemView.findViewById(R.id.tvLikesNum);

            itemView.setOnClickListener(view -> {
                if(itemClickInterface != null){
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        itemClickInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;
    private ItemClickInterface itemClickInterface;


    public SmallPostsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        itemClickInterface = (ItemClickInterface) context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.small_post_for_profile, parent, false);
        return new PostViewHolder(itemView, itemClickInterface);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvDish.setText(current.getDish_name());
            holder.tvLikesNum.setText(String.valueOf(current.getLikes()));
        }
    }

    public void setPosts(List<Post> s){
        posts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
