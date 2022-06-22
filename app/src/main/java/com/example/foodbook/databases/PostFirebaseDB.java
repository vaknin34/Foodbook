package com.example.foodbook.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodbook.models.Post;
import com.example.foodbook.repositories.PostsRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostFirebaseDB {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Posts");
    private PostDao post_dao;
    private PostsRepository.PostListData postListData;

    public PostFirebaseDB(PostDao post_dao, PostsRepository.PostListData postListData) {
        this.post_dao = post_dao;
        this.postListData = postListData;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Post> posts = new ArrayList<>();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Post post = postsnap.getValue(Post.class);
                    posts.add(post);
                    Log.d("Data", "Value is: " + post.getWriter() + post.getId());
                }
                postListData.setValue(posts);
                new Thread(()->{
                    post_dao.clear();
                    post_dao.insertAll(posts);
                }).start();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Data", "Failed to read value.", error.toException());
            }
        });
    }
    public void AddPost(Post post){

        myRef.child(String.valueOf(post.getId())).setValue(post);

    }

    public void delete(Post post) {
        myRef.child(String.valueOf(post.getId())).removeValue();
    }
    
    public void reload() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Post post = postsnap.getValue(Post.class);
                    posts.add(post);
                    Log.d("Data", "Value is: " + post.getWriter() + post.getId());
                }
                postListData.setValue(posts);
                new Thread(()->{
                    post_dao.clear();
                    post_dao.insertAll(posts);
                }).start();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Data", "Failed to read value.", error.toException());
            }
        });

    }

    public void update(Post post) {
        myRef.child(post.getId()).child("dish_name").setValue(post.getDish_name());
        myRef.child(post.getId()).child("ingredients").setValue(post.getIngredients());
        myRef.child(post.getId()).child("recipe").setValue(post.getRecipe());
    }
}