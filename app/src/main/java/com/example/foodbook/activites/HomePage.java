package com.example.foodbook.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodbook.R;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.models.Post;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import interfaces.ItemClickInterface;


public class HomePage extends AppCompatActivity implements ItemClickInterface {

    private ActivityHomePageBinding binding;
    private PostViewModel viewModel;
    private FirebaseUser current_user;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        final PostsListAdapter adapter = new PostsListAdapter(this);
        binding.lstPosts.setAdapter(adapter);
        binding.lstPosts.setLayoutManager(new LinearLayoutManager(this));

        binding.postDishBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreatePost.class);
            intent.putExtra(getString(R.string.user), current_user);
            startActivity(intent);
        });

        posts = new ArrayList<>();
        posts.add(new Post("Pasta","Yuval","6.5.22","pasta","....","SADSADSA",4));
        adapter.setPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        current_user = (FirebaseUser) getIntent().getExtras().get(getString(R.string.user));
        Log.d("Niv", current_user.getEmail());
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PostDetails.class);
        intent.putExtra(getString(R.string.dish_name), posts.get(position).getDish_name());
        startActivity(intent);
    }
}





