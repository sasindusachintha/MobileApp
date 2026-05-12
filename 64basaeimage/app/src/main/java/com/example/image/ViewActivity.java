package com.example.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class ViewActivity extends AppCompatActivity {

    LinearLayout container;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        container = findViewById(R.id.container);

        db = FirebaseDatabase.getInstance().getReference("products");

        fetchData();
    }

    private void fetchData() {

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                container.removeAllViews();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Product p = data.getValue(Product.class);

// TEXT
                    TextView tv = new TextView(ViewActivity.this);
                    tv.setText(p.description);
                    tv.setTextSize(20);

// IMAGE
                    ImageView iv = new ImageView(ViewActivity.this);

                    byte[] decodedBytes =
                            Base64.decode(p.imageBase64,
                                    Base64.DEFAULT);

                    Bitmap bitmap =
                            BitmapFactory.decodeByteArray(
                                    decodedBytes,
                                    0,
                                    decodedBytes.length);

                    iv.setImageBitmap(bitmap);

                    iv.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    500,
                                    500
                            ));

// ADD TO SCREEN
                    container.addView(tv);
                    container.addView(iv);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}

