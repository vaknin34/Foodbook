package com.example.foodbook.databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageManager {
    // Create a Cloud Storage reference from the app
    static StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public static void uploadImage(String image_path, byte[] image){
        StorageReference dishRef = storageRef.child(image_path);
        dishRef.getBytes(1000000000).addOnSuccessListener(bytes -> {
            dishRef.delete();
            StorageReference newDishRef = storageRef.child(image_path);
            newDishRef.putBytes(image);
        }).addOnFailureListener(e -> {
            dishRef.putBytes(image);
        });
    }


    public static void replaceImagePath(String new_image_path, String old_image_path){
        StorageReference dishRef = storageRef.child(old_image_path);
        dishRef.getBytes(1000000000).addOnSuccessListener(bytes -> {
            StorageReference newDishRef = storageRef.child(new_image_path);
            newDishRef.putBytes(bytes);
            dishRef.delete();
        });
    }

    public static void downloadImage(String path, ImageView imageView){
        StorageReference dishRef = storageRef.child(path);
        dishRef.getBytes(1000000000).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageView.setImageBitmap(bitmap);
        });
    }





}
