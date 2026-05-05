package com.example.sampleapp2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText email,password;
    Button resister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.txt_email1);
        password = findViewById(R.id.txt_Password);
        resister = findViewById(R.id.btn_regi);

        mAuth = FirebaseAuth.getInstance();

        resister.setOnClickListener( v -> {
            String userEmail= email.getText().toString();
            String userPass = password.getText().toString();

            mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(task -> {
               if (task.isSuccessful()){
                   Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
               }
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}