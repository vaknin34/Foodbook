package com.example.foodbook.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityProfileBinding;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseUser;

import interfaces.ItemClickInterface;

public class Profile extends AppCompatActivity implements ItemClickInterface {
    ActivityProfileBinding binding;
    private SmallPostsAdapter adapter;
    private PostViewModel viewModel;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new SmallPostsAdapter(this);
        binding.RVposts.setAdapter(adapter);
        binding.RVposts.setLayoutManager(new LinearLayoutManager(this));

        viewModel.get().observe(this, adapter::setPosts);

        FirebaseStorageManager.downloadImage(current_user.getEmail() + "profile", binding.profilePhoto);

    }

    @Override
    public void onItemClick(int position) {

    }
}