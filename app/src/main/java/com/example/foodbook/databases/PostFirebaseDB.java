package com.example.foodbook.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodbook.models.Like;
import com.example.foodbook.models.LikeStatus;
import com.example.foodbook.models.Post;
import com.example.foodbook.models.User;
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
    private DatabaseReference myLikeRef = database.getReference("Likes");
    private DatabaseReference myUserRef = database.getReference("Users");
    private PostDao post_dao;
    private UserDao user_dao;

    private PostsRepository.PostListData postListData;
    private PostsRepository.UserListData userListData;

    public PostFirebaseDB(PostDao post_dao, UserDao user_dao, PostsRepository.PostListData postListData, PostsRepository.UserListData userListData) {
        this.post_dao = post_dao;
        this.user_dao = user_dao;
        this.postListData = postListData;
        this.userListData = userListData;

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

        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<User> users = new ArrayList<>();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    User user = postsnap.getValue(User.class);
                    users.add(user);
                }
                userListData.setValue(users);
                new Thread(()->{
                    user_dao.clear();
                    user_dao.insertAll(users);
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

    public void AddUser(User user) {
        String id = user.getMail().replace(".", "").replace("@", "");
        myUserRef.child(id).setValue(user);
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
        myRef.child(post.getId()).child("likes").setValue(post.getLikes());
    }

    public void getLikeStatus(String user_mail, String post_id, LikeStatus likeStatus) {
        String id = user_mail.replace(".", "").replace("@", "") + post_id;
        myLikeRef.child(id).get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.getValue() == null)
                likeStatus.setStatus("likeNotPressed");
            else {
                likeStatus.setStatus("likePressed");
            }
        });
    }

    public void addLike(Like like) {
        String id = like.getUser_mail().replace(".", "").replace("@", "") + like.getPost_id();
        myLikeRef.child(id).setValue(like);
    }

    public void removeLike(String user_mail, String post_id) {
        String id = user_mail.replace(".", "").replace("@", "") + post_id;
        myLikeRef.child(id).removeValue();
    }
}