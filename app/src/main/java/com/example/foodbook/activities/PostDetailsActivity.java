package com.example.foodbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityPostDetailsBinding;
import com.example.foodbook.databinding.ActivityRegisterBinding;
import com.example.foodbook.models.Post;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;
    FirebaseUser current_user;
    PostViewModel viewModel;
    private static final int REQUEST_CODE = 200;
    Bitmap image;
    byte[] image_bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        current_user = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        Post post = (Post) getIntent().getSerializableExtra(getString(R.string.postDetails));

        binding.etDishName.getEditText().setText(post.getDish_name());
        binding.etWriter.getEditText().setText(post.getWriter());
        binding.etDate.getEditText().setText(post.getDate());
        binding.etIngredients.getEditText().setText(post.getIngredients());
        binding.etRecipe.getEditText().setText(post.getRecipe());
        FirebaseStorageManager.downloadImage(post.getMail() + post.getDish_name(), binding.ivDishPhoto);

        if (current_user.getEmail().equals(post.getMail())) {
            binding.deleteBtn.setVisibility(View.VISIBLE);
            binding.editBtn.setVisibility(View.VISIBLE);

            binding.ivDishPhoto.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
            });

            binding.deleteBtn.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(this)
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            Intent intent = new Intent(this, NavActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            viewModel.delete(post);
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {})
                        .show();
            });

            binding.editBtn.setOnClickListener(view -> {
                GetEditState();

                binding.saveBtn.setOnClickListener(view1 -> {
                    GetNonEditState();
                    FirebaseStorageManager.replaceImagePath(current_user.getEmail() + binding.etDishName.getEditText().getText().toString(), current_user.getEmail() + post.getDish_name());
                    post.setDish_name(binding.etDishName.getEditText().getText().toString());
                    post.setIngredients(binding.etIngredients.getEditText().getText().toString());
                    post.setRecipe(binding.etRecipe.getEditText().getText().toString());
                    viewModel.update(post);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    String firebase_image_path = post.getMail() + "profile";
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image_bytes = stream.toByteArray();
                    if (image_bytes.length > 0) {
                        FirebaseStorageManager.uploadImage(firebase_image_path, image_bytes);
                    }
                });

                binding.cnlBtn.setOnClickListener(view1 -> {
                    GetNonEditState();
                    binding.etDishName.getEditText().setText(post.getDish_name());
                    binding.etIngredients.getEditText().setText(post.getIngredients());
                    binding.etRecipe.getEditText().setText(post.getRecipe());
                });
            });
        }
    }

    private void GetEditState() {
        binding.deleteBtn.setVisibility(View.INVISIBLE);
        binding.editBtn.setVisibility(View.INVISIBLE);
        binding.saveBtn.setVisibility(View.VISIBLE);
        binding.cnlBtn.setVisibility(View.VISIBLE);
        binding.Clicktoeditlabel.setVisibility(View.VISIBLE);

        binding.etDishName.getEditText().setEnabled(true);
        binding.etIngredients.getEditText().setEnabled(true);
        binding.etRecipe.getEditText().setEnabled(true);
        binding.ivDishPhoto.setClickable(true);

    }

    private void GetNonEditState() {
        binding.deleteBtn.setVisibility(View.VISIBLE);
        binding.editBtn.setVisibility(View.VISIBLE);
        binding.saveBtn.setVisibility(View.INVISIBLE);
        binding.cnlBtn.setVisibility(View.INVISIBLE);
        binding.Clicktoeditlabel.setVisibility(View.INVISIBLE);

        binding.etDishName.getEditText().setEnabled(false);
        binding.etIngredients.getEditText().setEnabled(false);
        binding.etRecipe.getEditText().setEnabled(false);
        binding.ivDishPhoto.setClickable(false);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    //data gives you the image uri. Try to convert that to bitmap
                    Uri imageUri = data.getData();
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Toast.makeText(this, "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("Image", "Selecting picture cancelled");
                }
            }
        } catch (Exception e) {
            Log.e("Image", "Exception in onActivityResult : " + e.getMessage());
        }
    }
}