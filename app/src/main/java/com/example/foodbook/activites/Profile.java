package com.example.foodbook.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.databinding.ActivityProfileBinding;
import com.example.foodbook.viewmodels.PostViewModel;

import interfaces.ItemClickInterface;

public class Profile extends AppCompatActivity implements ItemClickInterface {
    ActivityProfileBinding binding;
    private SmallPostsAdapter adapter;
    private PostViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new SmallPostsAdapter(this);
        binding.RVposts.setAdapter(adapter);
        binding.RVposts.setLayoutManager(new LinearLayoutManager(this));

        viewModel.get().observe(this, adapter::setPosts);
    }

    @Override
    public void onItemClick(int position) {

    }
}