package com.example.foodbook.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.models.Like;
import com.example.foodbook.models.LikeStatus;
import com.example.foodbook.models.Post;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicReference;

import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;


public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWriter;
        private final TextView tvDishName;
        private final TextView tvDate;
        private final TextView tvLikes;
        private final ImageView ivImageFromFireBase;
        private final ImageButton like_btn;
        private final ImageView profilePhoto;

        private PostViewHolder(View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            tvWriter = itemView.findViewById(R.id.tvDish);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivImageFromFireBase = itemView.findViewById(R.id.ivDishImage);
            like_btn = itemView.findViewById(R.id.like_btn);
            profilePhoto = itemView.findViewById(R.id.profilePhoto);

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
    PostViewModel viewModel;


    public PostsListAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getContext());
        itemClickInterface = (ItemClickInterface) fragment;
        viewModel = new ViewModelProvider(fragment).get(PostViewModel.class);
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
            FirebaseStorageManager.downloadImage(current.getMail() + "profile" , holder.profilePhoto);
            FirebaseStorageManager.downloadImage(current.getMail() + current.getDish_name(), holder.ivImageFromFireBase);

            LikeStatus likeStatus;
            AtomicReference<String> local_like_status = new AtomicReference<>();
            Observer observer;

            likeStatus = new LikeStatus();

            observer = (observable, o) -> {
                if (o.equals("likePressed")) {
                    local_like_status.set("likePressed");
                    holder.like_btn.setImageResource(R.drawable.ic_unlike);

                } else {
                    local_like_status.set("likeNotPressed");
                    holder.like_btn.setImageResource(R.drawable.ic_like);
                }
            };

            likeStatus.addObserver(observer);

            viewModel.getLikeStatus(current.getMail(), current.getId(), likeStatus);


            holder.like_btn.setOnClickListener(view -> {
                if (local_like_status.get().equals("likeNotPressed")) {
                    current.setLikes(current.getLikes() + 1);
                    Like like = new Like(current.getMail(), current.getId());
                    viewModel.addLike(like);
                }
                else {
                    current.setLikes(current.getLikes() - 1);
                    viewModel.removeLike(current.getMail(), current.getId());
                }
                viewModel.update(current);
                holder.tvLikes.setText(String.valueOf(current.getLikes()));
            });
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