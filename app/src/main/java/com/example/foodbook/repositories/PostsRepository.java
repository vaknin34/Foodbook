package com.example.foodbook.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodbook.databases.PostDao;
import com.example.foodbook.databases.PostFirebaseDB;
import com.example.foodbook.databases.PostRoomDatabase;
import com.example.foodbook.models.Like;
import com.example.foodbook.models.LikeStatus;
import com.example.foodbook.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsRepository {
    private PostDao post_dao;
    private PostFirebaseDB post_fire_db;
    private PostListData postListData;
    private PostListData postListDataFilterEmail;
    private PostListData postListDataFilterWriterDishName;
    private static PostsRepository instance = new PostsRepository();

    private PostsRepository() {
        PostRoomDatabase db = PostRoomDatabase.getInstance();
        this.post_dao = db.postDao();
        this.postListData = new PostListData();
        this.postListDataFilterEmail = new PostListData();
        this.postListDataFilterWriterDishName = new PostListData();
        this.post_fire_db = new PostFirebaseDB(post_dao, postListData);
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

    public LiveData<List<Post>> getByWriterNameDishName(String writer, String dish_name) {
        new Thread(()->{
            if (writer.isEmpty() || dish_name.isEmpty())
                postListDataFilterWriterDishName.postValue(post_dao.findByWriterNameOrDishName(writer, dish_name));
            else
                postListDataFilterWriterDishName.postValue(post_dao.findByWriterNameAndDishName(writer, dish_name));
        }).start();
        return postListDataFilterWriterDishName;
    }

    public void add(Post post) {
        post_fire_db.AddPost(post);
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
}
