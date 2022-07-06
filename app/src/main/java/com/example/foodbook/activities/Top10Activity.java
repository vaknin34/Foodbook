package com.example.foodbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodbook.R;
import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.databinding.ActivityNavBinding;
import com.example.foodbook.databinding.ActivityTop10Binding;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;

import java.util.Collections;

public class Top10Activity extends AppCompatActivity implements ItemClickInterface {

    ActivityTop10Binding binding;
    private PostViewModel viewModel;
    private SmallPostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTop10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);
        adapter = new SmallPostsAdapter(this);

        binding.rvTop10.setAdapter(adapter);
        binding.rvTop10.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getTop10().observe(this, posts -> {
            Collections.sort(posts, (p1, p2) -> {
                Integer like1 = p1.getLikes();
                Integer like2 = p2.getLikes();
                return like2.compareTo(like1);
            });
            adapter.setPosts(posts);
        });
    }

    public void onItemClick(int position, String name) {
        Intent intent = new Intent(this, PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}