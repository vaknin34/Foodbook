package viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.Post;
import repositories.PostsRepository;

public class PostViewModel extends ViewModel {
    private LiveData<List<Post>> posts;
    private PostsRepository repository;

    public PostViewModel() {
        repository = new PostsRepository();
        posts = repository.getAll();
    }

    public LiveData<List<Post>> get() {return posts;}

    public void add(Post post) {repository.add(post);}

    public void delete(Post post) {repository.delete(post);}

    public void reload(){repository.reload();}





}
