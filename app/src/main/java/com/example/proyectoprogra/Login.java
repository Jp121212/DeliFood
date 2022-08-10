package com.example.proyectoprogra;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class Login extends AppCompatActivity {
    CircleImageView FbToast;
    CircleImageView TwToast;
    CircleImageView GgToast;
    Button btnLog1;
    Button btnLog2;
    Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLog1 = findViewById(R.id.btnLogin1);
        btnLog2 = findViewById(R.id.button3);
        btnSignup = findViewById(R.id.btnSign1);
        CircleImageView FbToast = (CircleImageView) findViewById(R.id.profile_face);
        CircleImageView  GgToast = (CircleImageView) findViewById(R.id.profile_google);
        CircleImageView TwToast = (CircleImageView) findViewById(R.id.profile_tw);

        FbToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),
                        "Conectando con Facebook...",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
        TwToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Conectando con Twitter...",Toast.LENGTH_LONG)
                        .show();
            }
        });
        GgToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Conectando con Google...",Toast.LENGTH_LONG)
                        .show();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });
        btnLog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Login.class);
            }
        });
        btnLog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Menu.class);
                startActivity(intent);
            }
        });

    }
}
