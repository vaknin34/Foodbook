package com.example.foodbook.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodbook.R;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import interfaces.ItemClickInterface;


public class HomePage extends AppCompatActivity implements ItemClickInterface {

    private ActivityHomePageBinding binding;
    private PostViewModel viewModel;
    private FirebaseUser current_user;
    private PostsListAdapter adapter;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new PostsListAdapter(this);
        binding.lstPosts.setAdapter(adapter);
        binding.lstPosts.setLayoutManager(new LinearLayoutManager(this));

        binding.postDishBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreatePost.class);
            intent.putExtra("user", firebaseAuth.getCurrentUser());
            startActivity(intent);
        });

        viewModel.get().observe(this, adapter::setPosts);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PostDetails.class);
        intent.putExtra(getString(R.string.postDetails), adapter.getPosts().get(position));
        startActivity(intent);
    }
}





