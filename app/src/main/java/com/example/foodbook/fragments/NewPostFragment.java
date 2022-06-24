package com.example.foodbook.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.foodbook.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.foodbook.viewmodels.PostViewModel;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.models.Post;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPostFragment extends Fragment {
    private static final int REQUEST_CODE = 200;
    FirebaseUser current_user;
    String dish_name, recipe, ingredients;
    Bitmap image;
    PostViewModel viewModel;
    byte[] image_bytes;
    SimpleDateFormat format;

    public NewPostFragment() {
    }

    public static NewPostFragment newInstance() {
        NewPostFragment fragment = new NewPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        view.findViewById(R.id.ivUploadDishPhoto).setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
        });


        view.findViewById(R.id.post_btn).setOnClickListener(view1 -> {
            dish_name = ((TextInputLayout)view.findViewById(R.id.etDishName)).getEditText().getText().toString();
            recipe = ((TextInputLayout)view.findViewById(R.id.etRecipe)).getEditText().getText().toString();
            ingredients = ((TextInputLayout)view.findViewById(R.id.etIngredients)).getEditText().getText().toString();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(image != null){
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image_bytes = stream.toByteArray();
            }

            format = new SimpleDateFormat("MM-dd HH:mm");

            if (dish_name.isEmpty()) {
                Toast.makeText(getContext(), "Dish name not valid", Toast.LENGTH_SHORT).show();
            }
            else if (recipe.isEmpty()) {
                Toast.makeText(getContext(), "Recipe not valid", Toast.LENGTH_SHORT).show();
            }
            else if (ingredients.isEmpty()) {
                Toast.makeText(getContext(), "Ingredients name not valid", Toast.LENGTH_SHORT).show();
            }
            else if (image == null) {
                Toast.makeText(getContext(), "Image not valid", Toast.LENGTH_SHORT).show();
            }

            else{
                String firebase_image_path = current_user.getEmail() + dish_name;
                Post post = new Post(dish_name, current_user.getDisplayName(), current_user.getEmail(), format.format(new Date()), ingredients, recipe, 0);
                FirebaseStorageManager.uploadImage(firebase_image_path, image_bytes);
                viewModel.add(post);
                Toast.makeText(getContext(), "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                ((TextInputLayout)view.findViewById(R.id.etDishName)).getEditText().setText("");
                ((TextInputLayout)view.findViewById(R.id.etRecipe)).getEditText().setText("");
                ((TextInputLayout)view.findViewById(R.id.etIngredients)).getEditText().setText("");
                ((TextInputLayout)view.findViewById(R.id.etIngredients)).getEditText().clearFocus();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    //data gives you the image uri. Try to convert that to bitmap
                    Uri imageUri = data.getData();
                    image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    Toast.makeText(getContext(), "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("Image", "Selecting picture cancelled");
                }
            }
        } catch (Exception e) {
            Log.e("Image", "Exception in onActivityResult : " + e.getMessage());
        }
    }
}