package com.boycillz.listasiancountries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String[] nameCountries = new String[]{
            "Indonesia", "Pakistan", "India",
            "Bangladesh", "Nigeria", "Egypt",
            "Iran", "Turkey", "Algeria",
            "Morocco", "Sudan", "Iraq",
            "Afghanistan", "Ethiopia", "Uzbekistan",
            "Saudi Arabia", "Yemen", "China",
            "Syria", "Malaysia", "Russia",
            "Kazakhstan", "Niger", "Tanzania"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Muslim Countries");

        listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (MainActivity.this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, nameCountries);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Memilih : "
                + nameCountries[position], Toast.LENGTH_LONG).show();
            }
        });
    }
}