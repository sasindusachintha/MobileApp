package com.example.newapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name, age, email, password ;

    Button btn_Save ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseReference databaseReference ;
        FirebaseDatabase database ;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn_Save = findViewById(R.id.btn_Save) ;

        name = findViewById(R.id.txt_name) ;
        age = findViewById(R.id.txt_age) ;
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password) ;

        databaseReference = FirebaseDatabase.getInstance().getReference() ;

        btn_Save.setOnClickListener(v -> {
            String empname = name.getText().toString();
            String empage = age.getText().toString();
            String empemail = email.getText().toString();
            String emppassword = password.getText().toString();

            if(empname.isEmpty() || empage.isEmpty() || empemail.isEmpty() || emppassword.isEmpty()){
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show() ;
            }

            String id = databaseReference.push().getKey() ;
            Employee employee = new Employee(id, empname, empage, empemail, emppassword) ;

            databaseReference.child(id).setValue(employee).addOnCompleteListener(task -> {
                Toast.makeText(MainActivity.this, "Employee Saved Successfully", Toast.LENGTH_SHORT).show() ;
            });

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
