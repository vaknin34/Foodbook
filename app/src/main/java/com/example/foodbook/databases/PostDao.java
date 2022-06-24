package com.example.foodbook.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodbook.models.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> getAll();

    @Query("SELECT * FROM post WHERE `Writer mail` LIKE :mail")
    List<Post> findByMail(String mail);

    @Query("SELECT * FROM post WHERE `dish name` LIKE '%' || :dish_name || '%'" )
    List<Post> findByDishName(String dish_name);

    @Query("SELECT * FROM post WHERE `Writer name` LIKE '%' || :user_name || '%'" )
    List<Post> findByUserName(String user_name);

    @Insert
    void insertAll(List<Post> posts);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);

    @Query("DELETE FROM post")
    void clear();
}
