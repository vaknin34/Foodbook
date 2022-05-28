package com.example.foodbook.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodbook.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostFirebaseDB {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Posts");

    public PostFirebaseDB(PostDao post_dao) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                Log.d("Data", "Value is: " + post.getWriter() + post.getId());
                post_dao.clear();
                post_dao.insertAll(post);
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
}