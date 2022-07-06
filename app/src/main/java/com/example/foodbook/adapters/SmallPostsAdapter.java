package com.example.foodbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.models.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.foodbook.interfaces.ItemClickInterface;


public class SmallPostsAdapter extends RecyclerView.Adapter<SmallPostsAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivDish;
        private final TextView tvName;
        private final TextView tvLikesNum;

        private PostViewHolder(View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            ivDish = itemView.findViewById(R.id.ivDish);
            tvName = itemView.findViewById(R.id.dishName);
            tvLikesNum = itemView.findViewById(R.id.tvLikesNum);

            itemView.setOnClickListener(view -> {
                if(itemClickInterface != null){
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        itemClickInterface.onItemClick(position, "smallPost");
                    }
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;
    private ItemClickInterface itemClickInterface;

    public SmallPostsAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getContext());
        itemClickInterface = (ItemClickInterface) fragment;
    }

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
            FirebaseStorageManager.downloadImage(current.getMail() + current.getDish_name() , holder.ivDish);
            holder.tvName.setText(current.getDish_name());
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
