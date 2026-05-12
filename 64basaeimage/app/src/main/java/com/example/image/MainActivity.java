package com.example.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText etDescription;

    Button btnPick, btnSave, btnView;

    Uri imageUri;

    DatabaseReference db;

    // IMAGE PICKER
    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            imageUri = uri;

                            imageView.setImageURI(uri);

                            Toast.makeText(
                                    MainActivity.this,
                                    "Image Selected",
                                    Toast.LENGTH_SHORT
                            ).show();

                            Log.d("IMAGE_PICKER",
                                    "Image Selected");
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // INIT
        imageView = findViewById(R.id.imageView);

        etDescription = findViewById(R.id.etDescription);

        btnPick = findViewById(R.id.btnPick);

        btnSave = findViewById(R.id.btnSave);

        btnView = findViewById(R.id.btnView);

        // FIREBASE
        db = FirebaseDatabase
                .getInstance()
                .getReference("products");

        // PICK IMAGE
        btnPick.setOnClickListener(v -> {

            try {

                imagePicker.launch("image/*");

            } catch (Exception e) {

                e.printStackTrace();

                Toast.makeText(
                        MainActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        // SAVE
        btnSave.setOnClickListener(v -> saveData());

        // VIEW PAGE
        btnView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ViewActivity.class
                    );

            startActivity(intent);
        });
    }

    private void saveData() {

        try {

            // CHECK IMAGE
            if (imageUri == null) {

                Toast.makeText(
                        this,
                        "Please Pick Image",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // DESCRIPTION
            String description =
                    etDescription
                            .getText()
                            .toString()
                            .trim();

            // OPEN IMAGE
            InputStream inputStream =
                    getContentResolver()
                            .openInputStream(imageUri);

            // BITMAP
            Bitmap bitmap =
                    BitmapFactory.decodeStream(inputStream);

            // COMPRESS
            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();

            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    60,
                    baos
            );

            byte[] imageBytes =
                    baos.toByteArray();

            // BASE64
            String base64Image =
                    Base64.encodeToString(
                            imageBytes,
                            Base64.DEFAULT
                    );

            // ID
            String id = db.push().getKey();

            // CORRECT PRODUCT OBJECT
            Product product =
                    new Product(
                            id,
                            description,
                            base64Image
                    );

            // SAVE TO FIREBASE
            db.child(id)
                    .setValue(product)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(
                                MainActivity.this,
                                "Saved Successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        Log.d("FIREBASE",
                                "Upload Success");

                        // CLEAR
                        etDescription.setText("");

                        imageView.setImageResource(
                                R.mipmap.ic_launcher
                        );

                        imageUri = null;
                    })
                    .addOnFailureListener(e -> {

                        Log.e("FIREBASE",
                                e.getMessage());

                        Toast.makeText(
                                MainActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });

        } catch (Exception e) {

            e.printStackTrace();

            Log.e("FIREBASE_ERROR",
                    e.getMessage());

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}