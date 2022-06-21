package com.example.foodbook.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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


    public void add(Post post) {repository.add(post);}

    public void delete(Post post) {repository.delete(post);}

    public void update(Post post) {repository.update(post);}

    public void reload(){repository.reload();}





}
