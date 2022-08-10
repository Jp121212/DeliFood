package com.example.proyectoprogra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Menu extends AppCompatActivity  {
  Button home,account,search,cart;
  ImageView diamante,gaming,of,health;
  ListView listView;
  TextView textView;

          int images[] = {R.drawable.kfc,R.drawable.pizza,R.drawable.descarga1,R.drawable.mac,R.drawable.bk};
          String names[] = {"KFC","PIZZA HUT","SATAY","MAC DONALDS","BURGUER KING"};
          List<ModeloItems> listitems = new ArrayList<>();
          CustomAdapter customAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        home = findViewById(R.id.home);
        listView = findViewById(R.id.lista);
        textView = findViewById(R.id.delivery);
        account = findViewById(R.id.account);
        search = findViewById(R.id.lupa);
        diamante = findViewById(R.id.buttonexc);
        gaming = findViewById(R.id.buttongaming);
        of = findViewById(R.id.btnofc);
        cart = findViewById(R.id.cart);
        health = findViewById(R.id.btnhealth);

        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Gaming.class);
                startActivity(intent);
            }
        });


        diamante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Exclusive.class);
                startActivity(intent);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Search.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Account.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Menu.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Cart.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Delivery.class);
                startActivity(intent);
            }
        });


        for (int i=0; i < names.length; i++){
            ModeloItems itemsModel = new ModeloItems(names[i],images[i]);
            listitems.add(itemsModel);
        }

        customAdapter = new CustomAdapter(listitems,this);
        listView.setAdapter(customAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(Menu.this, Restaurant.class);
                    startActivity(intent);
                }
                else if (i == 1) {
                    Intent intent = new Intent(Menu.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 2) {
                    Intent intent = new Intent(Menu.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 3) {
                    Intent intent = new Intent(Menu.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 4) {
                    Intent intent = new Intent(Menu.this, Ejemplo.class);
                    startActivity(intent);
                }
            }

        });
    }

    public class CustomAdapter extends BaseAdapter implements Filterable{
        private List<ModeloItems> itemsModelList;
        private List<ModeloItems> ItemsModelListFiltered;
        private ArrayList<ModeloItems>TempArray;
        CustomFilter cs;


        private Context  context;

        public CustomAdapter(List<ModeloItems> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.ItemsModelListFiltered = itemsModelList;
            this.context = context;
            this.TempArray = (ArrayList<ModeloItems>) itemsModelList;
        }

        @Override
        public int getCount() {
            return ItemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertetView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.rowitems,null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView itemName = view.findViewById(R.id.itemName);

            imageView.setImageResource(ItemsModelListFiltered.get(position).getImage());
            itemName.setText(ItemsModelListFiltered.get(position).getName());

            return view;
        }


        @Override
        public Filter getFilter() {
            if(cs == null){
                cs = new CustomFilter();
            }

            return cs;
        }
        class CustomFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence != null && charSequence.length()>0){
                    charSequence = charSequence.toString().toUpperCase();
                }
                ArrayList<ModeloItems> filters = new ArrayList<>();
                for(int i=0; i<TempArray.size();i++){
                    if(TempArray.get(i).getName().toUpperCase().contains(charSequence));{
                        ModeloItems modeloItems = new ModeloItems(TempArray.get(i).getName(),TempArray.get(i).getImage());
                        filters.add(modeloItems);
                    }
                    results.count = filters.size();
                    results.values =filters;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ItemsModelListFiltered = (List<ModeloItems>)filterResults.values;
                notifyDataSetChanged();

            }
        }
    }
        }

