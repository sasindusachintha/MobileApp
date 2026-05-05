package com.example.newapp;

import android.annotation.SuppressLint;
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

    EditText email, password ;

    Button register ;

    FirebaseAuth nAuth ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.txtEmail) ;
        password = findViewById(R.id.txtPass) ;
        register = findViewById(R.id.btn);

        nAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> {
            String userEmail = email.getText().toString();
            String userPass =  password.getText().toString();

            nAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(this,"Registered!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
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
