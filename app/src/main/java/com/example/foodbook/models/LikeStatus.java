package com.example.foodbook.models;

import java.util.Observable;

public class LikeStatus extends Observable {
    String status = "like";

    public void setStatus(String status) {
        this.status = status;
        setChanged();
        notifyObservers(status);
    }
}
