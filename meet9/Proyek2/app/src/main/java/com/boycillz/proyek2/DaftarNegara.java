package com.boycillz.proyek2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DaftarNegara extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_negara);

        //untuk mengubah titlebar aplikasi
        getSupportActionBar().setTitle("Daftar Negara");

        //array daftar negara yang tampil (awal)
        ArrayList<String> alNegara = new ArrayList<>();
        alNegara.add("Indonesia");
        alNegara.add("Singapore");
        alNegara.add("Vietnam");
        alNegara.add("Thailand");
        alNegara.add("Malaysia");

        //deklarasi variable
        EditText etNegara = findViewById(R.id.et_negara);
        Button btnSubmit = findViewById(R.id.btn_submit);
        ListView lvNegara = findViewById(R.id.lv_negara);

        //menampilkan array negara ke listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alNegara);
        lvNegara.setAdapter(adapter);


        //ketika button submit di-klik
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaNegara = etNegara.getText().toString();
                //validasi jika inputan tidak kosong
                if(!namaNegara.equals("")) {
//                    adapter.add(namaNegara);
                    alNegara.add(namaNegara);
                    adapter.notifyDataSetChanged();
                    etNegara.setText("");
                }
                else {
                    Toast.makeText(DaftarNegara.this, "Isian Kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}