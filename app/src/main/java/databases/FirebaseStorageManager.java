package databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageManager {
    // Create a Cloud Storage reference from the app
    static StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public void uploadImage(String writer, String dish_name, byte[] image){
        StorageReference dishRef = storageRef.child(writer + dish_name);
        dishRef.putBytes(image);
    }

    public static void downloadImage(String writer, String dish_name, ImageView imageView){
        StorageReference dishRef = storageRef.child(writer + dish_name);
        dishRef.getBytes(1000000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }





}
