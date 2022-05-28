package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Writer name")
    private String writer;
    @ColumnInfo(name = "date post was written")
    private String date;
    @ColumnInfo(name = "ingredients dish is made of")
    private String ingredients;
    @ColumnInfo(name = "the recipe of creating the dish")
    private String recipe;
    @ColumnInfo(name = "path to the photo of the dish")
    private String image_firebase_path;
    @ColumnInfo(name = "number of likes on the post")
    private int likes;


    public Post() { }

    public Post(String writer, String date, String ingredients, String recipe, String image_firebase_path, int likes) {
        this.writer = writer;
        this.date = date;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.image_firebase_path = image_firebase_path;
        this.likes = likes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public String getImage_firebase_path() {
        return image_firebase_path;
    }

    public void setImage_firebase_path(String image_firebase_path) {
        this.image_firebase_path = image_firebase_path;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
