package com.example.sampleapp2;

import android.content.Intent;
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

public class login extends AppCompatActivity {

    EditText email, password ;

    Button loginBtn ;

    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txt_mail) ;
        password = findViewById(R.id.txt_pass) ;
        loginBtn = findViewById(R.id.btn_login) ;

        mAuth = FirebaseAuth.getInstance() ;

        loginBtn.setOnClickListener(v -> {
            String userEmail = email.getText().toString() ;
            String userPassword = password.getText().toString() ;

         mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                 .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Login Successfully! ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, Dashboard.class));
                    }else{
                        Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show();
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