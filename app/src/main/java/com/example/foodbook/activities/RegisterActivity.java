package com.example.foodbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityRegisterBinding;
import com.example.foodbook.models.User;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final int REQUEST_CODE = 200;
    ActivityRegisterBinding binding;
    Bitmap image;
    byte[] image_bytes;
    String mail, password, name;
    PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        binding.signinBtn1.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.ivUploadProfilePhoto.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
        });

        binding.registerBtn.setOnClickListener(view -> {
            name = binding.etName.getEditText().getText().toString();
            mail = binding.etEmail.getEditText().getText().toString();
            password = binding.etPassword.getEditText().getText().toString();

            if (mail.isEmpty() || !mail.matches("^(.+)@(\\S+)$")) {
                Toast.makeText(this, R.string.Email_not_valid, Toast.LENGTH_SHORT).show();
            }
            else if (password.isEmpty() || password.length() < 6){
                Toast.makeText(this, R.string.Password_not_valid, Toast.LENGTH_SHORT).show();
            }
            else if (name.isEmpty()){
                Toast.makeText(this, R.string.Name_not_valid, Toast.LENGTH_SHORT).show();
            }
            else if (image == null){
                Toast.makeText(this, R.string.Image_not_valid, Toast.LENGTH_SHORT).show();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        currentUser.updateProfile(profileUpdates);
                        User user = new User(mail, name);
                        viewModel.addUser(user);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        String firebase_image_path = mail + "profile";
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        image_bytes = stream.toByteArray();
                        if (image_bytes.length > 0) {
                            FirebaseStorageManager.uploadImage(firebase_image_path, image_bytes);
                            Toast.makeText(this, R.string.User_created_successfully, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(this, R.string.Fail_to_save_picture, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, R.string.Fail_to_create_account, Toast.LENGTH_SHORT).show();
                    }
                });
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