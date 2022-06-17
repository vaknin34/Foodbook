package com.example.foodbook.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodbook.databases.PostDao;
import com.example.foodbook.databases.PostFirebaseDB;
import com.example.foodbook.databases.PostRoomDatabase;
import com.example.foodbook.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsRepository {
    private PostDao post_dao;
    private PostFirebaseDB post_fire_db;
    private PostListData postListData;
    private static PostsRepository instance = new PostsRepository();

    private PostsRepository() {
        PostRoomDatabase db = PostRoomDatabase.getInstance();
        this.post_dao = db.postDao();
        this.postListData = new PostListData();
        this.post_fire_db = new PostFirebaseDB(post_dao, postListData);
    }
    public static PostsRepository getInstance(){
        return instance;
    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void add(Post post) {
        post_fire_db.AddPost(post);
    }

    public void delete(Post post) {
        post_fire_db.delete(post);
    }

    public void reload() {
        post_fire_db.reload();
    }

    public class PostListData extends MutableLiveData<List<Post>> {
        public PostListData(){
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(()->{
               postListData.postValue(post_dao.getAll());
            }).start();
        }
    }
}
