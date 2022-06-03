package com.example.foodbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodbook.R;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseUser;



public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private PostViewModel viewModel;
    private FirebaseUser current_user;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        current_user = (FirebaseUser) getIntent().getExtras().get(getString(R.string.user));
        Log.d("Niv", current_user.getEmail());
    }
}





