package com.example.foodbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodbook.R;
import com.example.foodbook.activities.PostDetailsActivity;
import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.models.User;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TOP10Fragment extends Fragment implements ItemClickInterface {

    User user;
    private PostViewModel viewModel;
    private SmallPostsAdapter adapter;
    int postCount = 0;

    public TOP10Fragment() { }

    public static TOP10Fragment newInstance() {
        TOP10Fragment fragment = new TOP10Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_t_o_p10, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new SmallPostsAdapter(this);
        ((RecyclerView)view.findViewById(R.id.top10_rv)).setAdapter(adapter);
        ((RecyclerView)view.findViewById(R.id.top10_rv)).setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getTop10().observe(getViewLifecycleOwner(), posts -> {
            adapter.setPosts(posts);
        });
    }

    @Override
    public void onItemClick(int position, String name) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}