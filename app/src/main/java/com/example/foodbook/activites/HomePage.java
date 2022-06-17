package com.example.foodbook.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new PostsListAdapter(this);
        binding.lstPosts.setAdapter(adapter);
        binding.lstPosts.setLayoutManager(new LinearLayoutManager(this));

        viewModel.get().observe(this, posts -> {
            adapter.setPosts(posts);
            binding.swipeRefresh.setRefreshing(false);
        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.reload();
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent1 = new Intent(context, HomePage.class);
                    intent1.putExtra("user", current_user);
                    startActivity(intent1);
                    break;
                case R.id.profile:
                    Intent intent2 = new Intent(context, Profile.class);
                    intent2.putExtra("user", current_user);
                    startActivity(intent2);
                    break;
                case R.id.new_post:
                    Intent intent3 = new Intent(context, CreatePost.class);
                    intent3.putExtra("user", current_user);
                    startActivity(intent3);
                    break;
                case  R.id.log_out:
                    Intent intent4 = new Intent(this, LoginActivity.class);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent4);
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





