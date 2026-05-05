package com.example.sasinew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextView txtLogin;

    EditText name, phone, email, password ;

    Button registerBtn;

    FirebaseAuth auth ;

    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        txtLogin = findViewById(R.id.textView5);

        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
            finish(); // optional (prevents going back to register with back button)
        });

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtPhone);
        email = findViewById(R.id.txtMail);
        password = findViewById(R.id.txtPass);
        registerBtn = findViewById(R.id.btnSave);

        registerBtn.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userPass = password.getText().toString();

            auth.createUserWithEmailAndPassword(userEmail,userPass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // get id
                            String uid = auth.getCurrentUser().getUid();

                            //create user object
                            User user = new User(
                                    name.getText().toString(),
                                    phone.getText().toString(),
                                    userEmail
                            );

                            //save to real time database
                            databaseReference.child(uid).setValue(user);
                            Toast.makeText(this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, Login.class));
                        }else{
                            Toast.makeText(this,"Registerd Unsuccessfully",Toast.LENGTH_SHORT).show();
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