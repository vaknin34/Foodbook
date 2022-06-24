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
import com.example.foodbook.R;
import com.example.foodbook.activities.PostDetailsActivity;
import com.example.foodbook.adapters.SmallPostsAdapter;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment implements ItemClickInterface {

    private PostViewModel viewModel;
    private SmallPostsAdapter adapter;
    String writer_name;
    String dish_name;

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
            writer_name = ((TextInputEditText)view.findViewById(R.id.search_writer)).getText().toString();
            dish_name = ((TextInputEditText)view.findViewById(R.id.search_dish_name)).getText().toString();

            viewModel = new ViewModelProvider(this).get(PostViewModel.class);

            adapter = new SmallPostsAdapter(this);
            ((RecyclerView)view.findViewById(R.id.rv_serach_results)).setAdapter(adapter);
            ((RecyclerView)view.findViewById(R.id.rv_serach_results)).setLayoutManager(new LinearLayoutManager(this.getContext()));

            viewModel.getByWriterNameDishName(writer_name, dish_name).observe(getViewLifecycleOwner(), posts -> {
                adapter.setPosts(posts);
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter != null) {
            viewModel.getByWriterNameDishName(writer_name, dish_name).observe(getViewLifecycleOwner(), posts -> {
                adapter.setPosts(posts);
            });
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}