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

import java.util.List;

import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.models.User;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilPhoto;
        private final TextView tv_user_name;

        private PostViewHolder(View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            profilPhoto = itemView.findViewById(R.id.profilePhoto);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);

            itemView.setOnClickListener(view -> {
                if(itemClickInterface != null){
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        itemClickInterface.onItemClick(position, "user");
                    }
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;
    private ItemClickInterface itemClickInterface;

    public UsersAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getContext());
        itemClickInterface = (ItemClickInterface) fragment;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.user_for_search, parent, false);
        return new PostViewHolder(itemView, itemClickInterface);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            FirebaseStorageManager.downloadImage(current.getMail() + "profile" , holder.profilPhoto);
            holder.tv_user_name.setText(current.getWriter());
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

