package com.example.foodbook.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodbook.models.Post;
import com.example.foodbook.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE `name` LIKE '%' || :user_name || '%'" )
    List<User> findByUserName(String user_name);

    @Insert
    void insertAll(List<User> users);

    @Query("DELETE FROM user")
    void clear();

}
