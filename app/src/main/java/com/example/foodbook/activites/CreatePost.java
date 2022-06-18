package com.example.foodbook.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityCreatePostBinding;
import com.example.foodbook.models.Post;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePost extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;
    ActivityCreatePostBinding binding;
    FirebaseUser current_user;
    String dish_name, recipe, ingredients;
    Bitmap image;
    private PostViewModel viewModel;
    byte[] image_bytes;
    SimpleDateFormat format;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        context = this;

        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        binding.ivUploadDishPhoto.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
            binding.ivUploadDishPhoto.setImageBitmap(image);
        });


        binding.postBtn.setOnClickListener(view -> {
            dish_name = binding.etDishName.getEditText().getText().toString();
            recipe = binding.etRecipe.getEditText().getText().toString();
            ingredients = binding.etIngredients.getEditText().getText().toString();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(image != null){
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image_bytes = stream.toByteArray();
            }

            format = new SimpleDateFormat("MM-dd HH:mm");

            if(!dish_name.isEmpty() && !recipe.isEmpty() && !ingredients.isEmpty() && image_bytes.length > 0){
                String firebase_image_path = current_user.getDisplayName() + dish_name;
                Post post = new Post(dish_name, current_user.getDisplayName(), current_user.getEmail(), format.format(new Date()), ingredients, recipe, firebase_image_path, 0);
                FirebaseStorageManager.uploadImage(firebase_image_path, image_bytes);
                viewModel.add(post);
                Intent intent = new Intent(this, HomePage.class);
                intent.putExtra("user", current_user);
                startActivity(intent);
            }
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent1 = new Intent(context, HomePage.class);
                    intent1.putExtra("user", current_user);
                    startActivity(intent1);
                    break;
                case R.id.profile:
                    Intent intent2 = new Intent(context, Profile.class);
                    intent2.putExtra("user", current_user);
                    startActivity(intent2);
                    break;
                case R.id.new_post:
                    Intent intent3 = new Intent(context, CreatePost.class);
                    intent3.putExtra("user", current_user);
                    startActivity(intent3);
                    break;
                case  R.id.log_out:
                    Intent intent4 = new Intent(this, LoginActivity.class);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent4);
            }
            return true;
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