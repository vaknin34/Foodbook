package com.example.foodbook.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodbook.models.Like;
import com.example.foodbook.models.LikeStatus;
import com.example.foodbook.models.Post;
import com.example.foodbook.repositories.PostsRepository;

import java.util.List;

public class PostViewModel extends ViewModel {
    private LiveData<List<Post>> posts;
    private PostsRepository repository;

    public PostViewModel() {
        repository = PostsRepository.getInstance();
        posts = repository.getAll();
    }

    public LiveData<List<Post>> get() {return posts;}

    public LiveData<List<Post>> getByMail(String mail) {
        posts = repository.getByMail(mail);
        return posts;
    }

    public LiveData<List<Post>> getByUserName(String user_name) {
        posts = repository.getByUserName(user_name);
        return posts;
    }

    public LiveData<List<Post>> getByDishName(String dish_name) {
        posts = repository.getByDishName(dish_name);
        return posts;
    }

    public void add(Post post) {repository.add(post);}

    public void delete(Post post) {repository.delete(post);}

    public void update(Post post) {repository.update(post);}

    public void reload(){repository.reload();}

    public void getLikeStatus(String user_mail, String post_id, LikeStatus likeStatus){
        repository.getLikeStatus(user_mail, post_id, likeStatus);
    }

    public void addLike(Like like) {
        repository.addLike(like);
    }

    public void removeLike(String user_mail, String post_id) {
        repository.removeLike(user_mail, post_id);
    }
}
