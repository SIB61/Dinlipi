package com.sib4u.dinlipi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    private EditText editText1,editText2;
    private Button button;
    private String p;
    private String s;
   private UserDetail userDetail;
   Theme theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDetail=new UserDetail(this);
        if(!userDetail.lockStatus())
        {
            startActivity(new Intent(MainActivity.this, secondActivity.class));
            finish();
        }
        editText1 = findViewById(R.id.usernameID);
        editText2 = findViewById(R.id.passwordID);
        button = findViewById(R.id.button);
        p = userDetail.load("passWordKey");
        if (p.isEmpty())
            button.setText("save");
        else {
            button.setText("Open");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordField = editText2.getText().toString().trim();
                String usernameField = editText1.getText().toString().trim();
                if (usernameField.isEmpty()) {
                    Toast.makeText(MainActivity.this, "enter username", LENGTH_SHORT).show();
                } else if (passwordField.isEmpty()) {
                    Toast.makeText(MainActivity.this, "enter password", LENGTH_SHORT).show();
                } else {
                    if (p.isEmpty()) {
                        userDetail.save( usernameField, passwordField);
                        startActivity(new Intent(MainActivity.this, secondActivity.class));
                        finish();
                    } else {
                        s = userDetail.load("userNameKey");
                        if (s.equals(usernameField) && p.equals(passwordField)) {
                            startActivity(new Intent(MainActivity.this, secondActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "invalid username or password", LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
