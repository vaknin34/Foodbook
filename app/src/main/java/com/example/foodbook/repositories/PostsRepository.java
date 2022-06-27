package com.example.foodbook.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodbook.databases.PostDao;
import com.example.foodbook.databases.PostFirebaseDB;
import com.example.foodbook.databases.PostRoomDatabase;
import com.example.foodbook.databases.UserDao;
import com.example.foodbook.models.Like;
import com.example.foodbook.models.LikeStatus;
import com.example.foodbook.models.Post;
import com.example.foodbook.models.User;

import java.util.ArrayList;
import java.util.List;

public class PostsRepository {
    private PostDao post_dao;
    private UserDao user_dao;
    private PostFirebaseDB post_fire_db;
    private PostListData postListData;
    private PostListData postListDataFilterEmail;
    private PostListData postListDataFilterDishName;
    private UserListData userListData;
    private static PostsRepository instance = new PostsRepository();

    private PostsRepository() {
        PostRoomDatabase db = PostRoomDatabase.getInstance();
        this.post_dao = db.postDao();
        this.user_dao = db.userDao();
        this.postListData = new PostListData();
        this.postListDataFilterEmail = new PostListData();
        this.postListDataFilterDishName = new PostListData();
        this.userListData = new UserListData();
        this.post_fire_db = new PostFirebaseDB(post_dao, user_dao, postListData, userListData);
    }
    public static PostsRepository getInstance(){
        return instance;
    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public LiveData<List<Post>> getByMail(String mail) {
        new Thread(()->{
            postListDataFilterEmail.postValue(post_dao.findByMail(mail));
        }).start();
        return postListDataFilterEmail;
    }

    public LiveData<List<Post>> getByDishName(String dish_name) {
        new Thread(()->{
            postListDataFilterDishName.postValue(post_dao.findByDishName(dish_name));
        }).start();
        return postListDataFilterDishName;
    }

    public LiveData<List<User>> getByUserName(String user_name) {
        new Thread(()->{
            userListData.postValue(user_dao.findByUserName(user_name));
        }).start();
        return userListData;
    }

    public void add(Post post) {
        post_fire_db.AddPost(post);
    }

    public void addUser(User user) {
        post_fire_db.AddUser(user);
    }

    public void delete(Post post) {
        post_fire_db.delete(post);
    }

    public void update(Post post) {
        post_fire_db.update(post);
    }

    public void reload() {
        post_fire_db.reload();
    }

    public void getLikeStatus(String user_mail, String post_id, LikeStatus likeStatus) {
        post_fire_db.getLikeStatus(user_mail, post_id, likeStatus);
    }

    public void addLike(Like like) {
        post_fire_db.addLike(like);
    }

    public void removeLike(String user_mail, String post_id) {
        post_fire_db.removeLike(user_mail, post_id);
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

    public class UserListData extends MutableLiveData<List<User>> {
        public UserListData(){
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }
}
