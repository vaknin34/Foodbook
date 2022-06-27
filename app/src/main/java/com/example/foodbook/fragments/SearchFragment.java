package com.example.foodbook.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodbook.R;
import com.example.foodbook.activities.PostDetailsActivity;
import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.adapters.UsersAdapter;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.models.Post;
import com.example.foodbook.models.User;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            view.findViewById(R.id.users_title).setVisibility(View.VISIBLE);
            view.findViewById(R.id.posts_title).setVisibility(View.VISIBLE);

            query = ((TextInputEditText)view.findViewById(R.id.search_query)).getText().toString();

            viewModel = new ViewModelProvider(this).get(PostViewModel.class);

            usersAdapter = new UsersAdapter(this);
            ((RecyclerView)view.findViewById(R.id.rv_user_results)).setAdapter(usersAdapter);
            ((RecyclerView)view.findViewById(R.id.rv_user_results)).setLayoutManager(new LinearLayoutManager(this.getContext()));

            postsAdapter = new SmallPostsAdapter(this);
            ((RecyclerView)view.findViewById(R.id.rv_post_results)).setAdapter(postsAdapter);
            ((RecyclerView)view.findViewById(R.id.rv_post_results)).setLayoutManager(new LinearLayoutManager(this.getContext()));

            viewModel.getByUserName(query);

            if (usersAdapter != null) {
                viewModel.getByUserName(query).observe(getViewLifecycleOwner(), users -> {
                    usersAdapter.setUsers(users);
                });
            }

            if (postsAdapter != null) {
                viewModel.getByDishName(query).observe(getViewLifecycleOwner(), posts -> {
                    postsAdapter.setPosts(posts);
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (usersAdapter != null) {
            viewModel.getByUserName(query).observe(getViewLifecycleOwner(), users -> {
                usersAdapter.setUsers(users);
            });
        }

        if (postsAdapter != null) {
            viewModel.getByDishName(query).observe(getViewLifecycleOwner(), posts -> {
                postsAdapter.setPosts(posts);
            });
        }

    }

    @Override
    public void onItemClick(int position, String name) {
        if (name.equals("user")) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            User user = new User(usersAdapter.getUsers().get(position).getMail(), usersAdapter.getUsers().get(position).getName());
            transaction.replace(R.id.fragmentsFrame, ProfileFragment.newInstance(user), "whatever");
            transaction.commit();
        }
        else {
            Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
            intent.putExtra("postDetails", postsAdapter.getPosts().get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}