package com.example.proyectoprogra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.Locale;

public class Search extends AppCompatActivity implements TextWatcher {
    EditText el;
    ListView ll;
    Button home,account,search,cart;
    String[] name = {"KFC","Burguer King","Pizza Hut","Satay","Mac Donalds"};
    int[] images = {R.drawable.kfc,R.drawable.bk,R.drawable.pizza,R.drawable.descarga1,R.drawable.mac};
    ArrayList<SingleRow> myList;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        el = (EditText) findViewById(R.id.EdtBuscar);
        ll = (ListView) findViewById(R.id.listview2);
        account = findViewById(R.id.account);
        search = findViewById(R.id.lupa);
        home = findViewById(R.id.home);
        cart = findViewById(R.id.cart);
        el.addTextChangedListener(this);
        myList = new ArrayList<>();
        SingleRow singleRow;

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this,Search.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, Cart.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, Account.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this,Menu.class);
                startActivity(intent);
            }
        });


        for(int i=0; i< name.length;i++){
            singleRow = new SingleRow(name[i],images[i]);
            myList.add(singleRow);
        }
        myAdapter = new MyAdapter(this,myList);
        ll.setAdapter(myAdapter);
        ll.setClickable(true);
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(Search.this, Restaurant.class);
                    startActivity(intent);
                }
                else if (i == 1) {
                    Intent intent = new Intent(Search.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 2) {
                    Intent intent = new Intent(Search.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 3) {
                    Intent intent = new Intent(Search.this, Ejemplo.class);
                    startActivity(intent);
                }
                else if (i == 4) {
                    Intent intent = new Intent(Search.this, Ejemplo.class);
                    startActivity(intent);
                }
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.myAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public class MyAdapter extends BaseAdapter implements Filterable {
        Context c;
        ArrayList<SingleRow> orginalArray,tempArray;
        CustomFilter cs;

        public MyAdapter (Context c, ArrayList<SingleRow> originalArray){
            this.c=c;
            this.orginalArray=originalArray;
            this.tempArray=originalArray;
        }

        @Override
        public int getCount() {
            return orginalArray.size();
        }

        @Override
        public Object getItem(int i) {
            return orginalArray.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_listview,null);
            TextView t1 = (TextView)row.findViewById(R.id.txtRestaurant);
            ImageView i1 = (ImageView)row.findViewById(R.id.ImRestaurant);

            t1.setText(orginalArray.get(i).getName());
            i1.setImageResource(orginalArray.get(i).getImage());
            return row;
        }

        @Override
        public Filter getFilter() {
            if(cs == null){
                cs= new CustomFilter();
            }
            return cs;
        }
        class CustomFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results= new FilterResults();
                if(constraint!=null && constraint.length()>0) {
                    constraint = constraint.toString().toUpperCase();
                    ArrayList<SingleRow> filters = new ArrayList<>();
                    for (int i = 0; i < tempArray.size(); i++) {
                        if (tempArray.get(i).getName().toUpperCase().contains(constraint)) {
                            SingleRow singleRow = new SingleRow(tempArray.get(i).getName(), tempArray.get(i).getImage());
                            filters.add(singleRow);
                        }
                    }
                    results.count=filters.size();
                    results.values=filters;
                }
                else{
                    results.count=tempArray.size();
                    results.values=tempArray;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                orginalArray= (ArrayList<SingleRow>)filterResults.values;
                notifyDataSetChanged();

            }
        }
    }
}

