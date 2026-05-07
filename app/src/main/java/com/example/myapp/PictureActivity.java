package com.example.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PictureActivity extends AppCompatActivity {
    private ImageView imageView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar materialToolbar;
private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
    @Override
    public void onActivityResult(Boolean isGranted) {
        if(isGranted){

        }
        else {
            Toast.makeText(PictureActivity.this,"נדרשת הרשאת מצלמה בכדי לצלם תמונה",Toast.LENGTH_SHORT).show();
        }
    }
});
    private Uri saveImageToInternalStorage(Bitmap bitmap) {
        String imageName = "image_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(getApplicationContext().getFilesDir(),
                "YourAppImages");
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Toast.makeText(this, "Failed to create directory",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        }
        File imageFile = new File(storageDir, imageName);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(this, "Image saved to internal storage: " +
                    imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            return Uri.fromFile(imageFile);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to save image",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }

    private ActivityResultLauncher<Intent> takePictureLauncher =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == Activity.RESULT_OK) {
                                Intent data = o.getData();

                                Bundle extras = data.getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                Uri uri = saveImageToInternalStorage(imageBitmap);
                                imageView.setImageURI(uri);
                            } else {
                                Toast.makeText(PictureActivity.this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    private ActivityResultLauncher<String> pickImageLauncher =registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri o) {
                            if (o != null) {
                               Uri uri = copyImageToAppDir(o);
                               String string = uri.toString();
                               Uri parse = Uri.parse(string);
                               imageView.setImageURI(parse);
                            }
                        }
                    });


    private Uri copyImageToAppDir(Uri contentUri) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = getContentResolver().openInputStream(contentUri);
            File outputDir = getApplicationContext().getFilesDir();
            File outputFile = new File(outputDir, "copied_image_" + System.currentTimeMillis() + ".jpg");
            os = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();

            return Uri.fromFile(outputFile);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to copy image: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
            @Override
            protected void onCreate (Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_picture);
                drawerLayout = findViewById(R.id.DrawerLayout);
                navigationView = findViewById(R.id.NavigationView);
                materialToolbar = findViewById(R.id.materialToolbar);
                SomeClass.navigationSolution(drawerLayout,materialToolbar,navigationView,this);
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                pickImageLauncher.launch("image/*");
                imageView = findViewById(R.id.imageView1);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureLauncher.launch(takePictureIntent);
                }

            }

}