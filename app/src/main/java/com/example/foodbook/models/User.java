package com.example.foodbook.models;

import java.io.Serializable;

public class User implements Serializable {
    String mail;
    String name;

    public User(String mail, String name) {
        this.mail = mail;
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
