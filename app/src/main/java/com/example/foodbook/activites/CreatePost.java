package com.example.foodbook.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityCreatePostBinding;
import com.example.foodbook.models.Post;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class CreatePost extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;
    ActivityCreatePostBinding binding;
    FirebaseUser current_user;
    String dish_name, recipe, ingredients;
    Bitmap image;
    byte[] image_bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //        viewModel = new ViewModelProvider(this).get(PostViewModel.class);


        current_user = (FirebaseUser) getIntent().getExtras().get(getString(R.string.user));

        binding.ivUploadDishPhoto.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
            binding.ivUploadDishPhoto.setImageBitmap(image);
        });


        binding.postBtn.setOnClickListener(view -> {
            dish_name = binding.etDishName.getText().toString();
            recipe = binding.etRecipe.getText().toString();
            ingredients = binding.etIngredients.getText().toString();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(image != null){
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image_bytes = stream.toByteArray();
            }

            if(!dish_name.isEmpty() && !recipe.isEmpty() && !ingredients.isEmpty() && image_bytes.length > 0){
                String firebase_image_path = current_user.getDisplayName() + dish_name;
                Post post = new Post(dish_name, current_user.getDisplayName(), String.valueOf(new Date()), ingredients, recipe, firebase_image_path, 0);
                FirebaseStorageManager.uploadImage(firebase_image_path, image_bytes);
                //viewmodel.addpost(post);
                Intent intent = new Intent(this, HomePage.class);
                intent.putExtra(getString(R.string.user), current_user);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    //data gives you the image uri. Try to convert that to bitmap
                    Uri imageUri = data.getData();
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("Image", "Selecting picture cancelled");
                }
            }
        } catch (Exception e) {
            Log.e("Image", "Exception in onActivityResult : " + e.getMessage());
        }
    }
}