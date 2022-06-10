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

    @Query("SELECT * FROM post WHERE `Writer name` LIKE :writer")
    List<Post> findByName(String writer);

    @Insert
    void insertAll(List<Post> posts);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);

    @Query("DELETE FROM Post")
    void clear();
}
