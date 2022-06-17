package com.example.foodbook.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodbook.R;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import interfaces.ItemClickInterface;


public class HomePage extends AppCompatActivity implements ItemClickInterface {

    private ActivityHomePageBinding binding;
    private PostViewModel viewModel;
    private FirebaseUser current_user;
    private PostsListAdapter adapter;
    private FirebaseAuth firebaseAuth;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        firebaseAuth = FirebaseAuth.getInstance();
        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new PostsListAdapter(this);
        binding.lstPosts.setAdapter(adapter);
        binding.lstPosts.setLayoutManager(new LinearLayoutManager(this));

        viewModel.get().observe(this, adapter::setPosts);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent1 = new Intent(context, HomePage.class);
                    intent1.putExtra("user", current_user);
                    startActivity(intent1);
                    break;
                case R.id.profile:
                    Intent intent2 = new Intent(context, Profile.class);
                    intent2.putExtra("user", firebaseAuth.getCurrentUser());
                    startActivity(intent2);
                    break;
                case R.id.new_post:
                    Intent intent3 = new Intent(context, CreatePost.class);
                    intent3.putExtra("user", firebaseAuth.getCurrentUser());
                    startActivity(intent3);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PostDetails.class);
        intent.putExtra(getString(R.string.postDetails), adapter.getPosts().get(position));
        startActivity(intent);
    }
}





