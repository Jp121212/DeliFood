package com.example.proyectoprogra;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class Restaurant extends AppCompatActivity {
    ImageView rest;
    TextView name,add1,add2,price1,price2,nombreproducto,nombreproducto2;
    Button back;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        rest = findViewById(R.id.ImRestaurant);
        name = findViewById(R.id.itemName);
        back = findViewById(R.id.back2);
        add1 = findViewById(R.id.Add);
        add2 = findViewById(R.id.add1);
        price1 = findViewById(R.id.precio1);
        price2 = findViewById(R.id.precio2);
        nombreproducto = findViewById(R.id.nombreproducto);
        nombreproducto2 = findViewById(R.id.textView19);
        img = findViewById(R.id.kfccombo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Restaurant.this,   Menu.class);
                startActivity(intent);
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = price1.getText().toString();
                String str2 = nombreproducto.getText().toString();
                Intent i = new Intent(Restaurant.this,Cart.class);
                i.putExtra("Product", str2);
                i.putExtra("Price", str);
                i.putExtra("Image",R.drawable.kfccombo);
                startActivity(i);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = price2.getText().toString();
                String str2 = nombreproducto2.getText().toString();
                Intent i = new Intent(Restaurant.this,Cart.class);
                i.putExtra("Product", str2);
                i.putExtra("Price", str);
                i.putExtra("Image",R.drawable._0);
                startActivity(i);
            }
        });

    }
}
