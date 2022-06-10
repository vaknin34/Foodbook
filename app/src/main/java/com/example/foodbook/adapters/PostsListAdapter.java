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

import java.util.List;

import interfaces.ItemClickInterface;


public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWriter;
        private final TextView tvDishName;
        private final TextView tvDate;
        private final TextView tvLikes;
        private final ImageView ivImageFromFireBase;

        private PostViewHolder(View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            tvWriter = itemView.findViewById(R.id.tvWriterName);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivImageFromFireBase = itemView.findViewById(R.id.ivDishImage);

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


    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        itemClickInterface = (ItemClickInterface) context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(itemView, itemClickInterface);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvWriter.setText(current.getWriter());
            holder.tvDishName.setText(current.getDish_name());
            holder.tvDate.setText(current.getDate());
            holder.tvLikes.setText(String.valueOf(current.getLikes()));
            FirebaseStorageManager.downloadImage(current.getWriter(), current.getDish_name(), holder.ivImageFromFireBase);
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