package repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import databases.PostDao;
import databases.PostFirebaseDB;
import databases.PostRoomDatabase;
import models.Post;

public class PostsRepository {
    private PostDao post_dao;
    private PostFirebaseDB post_fire_db;
    private PostListData postListData;

    public PostsRepository() {
        PostRoomDatabase db = PostRoomDatabase.getInstance();
        this.post_dao = db.postDao();
        this.post_fire_db = new PostFirebaseDB(post_dao);
        this.postListData = new PostListData();
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
        postListData.setValue(post_dao.getAll());
    }

    private class PostListData extends MutableLiveData<List<Post>> {
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
