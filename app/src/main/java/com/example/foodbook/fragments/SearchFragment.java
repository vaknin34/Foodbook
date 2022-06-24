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
import com.example.foodbook.adapters.UsersAdapter;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment implements ItemClickInterface {

    private PostViewModel viewModel;
    private SmallPostsAdapter postsAdapter;
    private UsersAdapter usersAdapter;

    String query;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.search_btn).setOnClickListener(view1 -> {
            query = ((TextInputEditText)view.findViewById(R.id.search)).getText().toString();

            viewModel = new ViewModelProvider(this).get(PostViewModel.class);

            usersAdapter = new UsersAdapter(this);
            ((RecyclerView)view.findViewById(R.id.rv_user_results)).setAdapter(usersAdapter);
            ((RecyclerView)view.findViewById(R.id.rv_user_results)).setLayoutManager(new LinearLayoutManager(this.getContext()));

            postsAdapter = new SmallPostsAdapter(this);
            ((RecyclerView)view.findViewById(R.id.rv_post_results)).setAdapter(usersAdapter);
            ((RecyclerView)view.findViewById(R.id.rv_post_results)).setLayoutManager(new LinearLayoutManager(this.getContext()));

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getUsersByUserName(query).observe(getViewLifecycleOwner(), users -> {
            usersAdapter.setUsers(users);
        });

        viewModel.getPostsByDishName(query).observe(getViewLifecycleOwner(), posts -> {
            postsAdapter.setPosts(posts);
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}