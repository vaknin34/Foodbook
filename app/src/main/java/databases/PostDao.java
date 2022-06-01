package databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import models.Post;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> getAll();

    @Query("SELECT * FROM post WHERE `Writer name` LIKE :writer")
    List<Post> findByName(String writer);

    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);

    @Query("DELETE FROM Post")
    void clear();
}
