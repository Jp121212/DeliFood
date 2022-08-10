package com.example.proyectoprogra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Ejemplo extends AppCompatActivity {
    Button back3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejemplo);
        back3 = findViewById(R.id.back3);

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ejemplo.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}

