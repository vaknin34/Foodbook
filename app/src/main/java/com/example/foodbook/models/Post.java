package com.example.foodbook.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Post implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "dish name")
    private String dish_name;
    @ColumnInfo(name = "Writer name")
    private String writer;
    @ColumnInfo(name = "Writer mail")
    private String mail;
    @ColumnInfo(name = "date post was written")
    private String date;
    @ColumnInfo(name = "ingredients dish is made of")
    private String ingredients;
    @ColumnInfo(name = "the recipe of creating the dish")
    private String recipe;
    @ColumnInfo(name = "number of likes on the post")
    private int likes;


    public Post() { }

    public Post(String dish_name, String writer, String mail, String date, String ingredients, String recipe, int likes) {
        this.id = java.util.UUID.randomUUID().toString().replace("-","");
        this.dish_name = dish_name;
        this.writer = writer;
        this.mail = mail;
        this.date = date;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.likes = likes;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
