package com.example.foodbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodbook.R;
import com.example.foodbook.activities.PostDetailsActivity;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment implements ItemClickInterface {

    FirebaseUser current_user;
    private PostViewModel viewModel;
    private SmallPostsAdapter adapter;
    int postCount = 0;

    public ProfileFragment() { }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseStorageManager.downloadImage(current_user.getEmail() + "profile" , view.findViewById(R.id.profilePhoto));
        ((TextView)view.findViewById(R.id.name)).setText(current_user.getDisplayName());

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new SmallPostsAdapter(this);
        ((RecyclerView)view.findViewById(R.id.smallPostsRv)).setAdapter(adapter);
        ((RecyclerView)view.findViewById(R.id.smallPostsRv)).setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getByMail(current_user.getEmail()).observe(getViewLifecycleOwner(), posts -> {
            postCount = posts.size();
            ((TextView)view.findViewById(R.id.postNum)).setText(postCount + " Posts");
            adapter.setPosts(posts);
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        startActivity(intent);
    }
}