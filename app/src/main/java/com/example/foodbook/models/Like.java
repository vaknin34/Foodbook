package com.example.foodbook.models;

public class Like {
    String user_mail;
    String post_id;

    public Like(String user_mail, String post_id) {
        this.user_mail = user_mail;
        this.post_id = post_id;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
