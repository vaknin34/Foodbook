package com.example.foodbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.foodbook.activities.PostDetailsActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodbook.R;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;

public class HomeFragment extends Fragment implements ItemClickInterface {

    private PostViewModel viewModel;
    private PostsListAdapter adapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new PostsListAdapter(this);
        ((RecyclerView)view.findViewById(R.id.lstPosts)).setAdapter(adapter);
        ((RecyclerView)view.findViewById(R.id.lstPosts)).setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.get().observe(getViewLifecycleOwner(), posts -> {
            adapter.setPosts(posts);
            ((SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh)).setRefreshing(false);
        });
        ((SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh)).setOnRefreshListener(() -> {
            viewModel.reload();
        });
        
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        startActivity(intent);
    }
}