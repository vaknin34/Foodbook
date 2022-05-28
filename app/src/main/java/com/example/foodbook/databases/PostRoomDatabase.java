package com.example.foodbook.databases;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodbook.MyApplication;
import com.example.foodbook.R;
import com.example.foodbook.models.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class PostRoomDatabase extends RoomDatabase {
    private static PostRoomDatabase instance;
    public abstract PostDao postDao();


    public static PostRoomDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(MyApplication.context.getApplicationContext(),
                    PostRoomDatabase.class,
                    MyApplication.context.getString(R.string.DbName)).build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
