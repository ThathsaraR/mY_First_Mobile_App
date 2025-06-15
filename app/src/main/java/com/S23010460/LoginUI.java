package com.S23010460;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class LoginUI extends AppCompatActivity {

    DatabaseHelper mydb;
    EditText username, password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        mydb = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(view -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginUI.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValid = mydb.checkUser(user, pass);
                if (isValid) {
                    Toast.makeText(LoginUI.this, "Login successful", Toast.LENGTH_SHORT).show();
                    //Navigation
                    startActivity(new Intent(LoginUI.this, MapUI.class));

                } else {
                    Toast.makeText(LoginUI.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
