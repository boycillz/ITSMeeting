package com.boycillz.kalkulatorits;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText angkaPertama, angkaKedua;
    Button btnTambah, btnKurang, btnKali, btnBagi, btnBersihkan;
    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angkaPertama = findViewById(R.id.angka_pertama);
        angkaKedua = findViewById(R.id.angka_kedua);
        btnBagi = findViewById(R.id.bagi);
        btnKali = findViewById(R.id.kali);
        btnTambah = findViewById(R.id.tambah);
        btnKurang = findViewById(R.id.kurang);
        btnBersihkan = findViewById(R.id.bersihkan);
        tvHasil = findViewById(R.id.hasil);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((angkaPertama.getText().length() > 0) && (angkaKedua.getText().length() > 0)) {
                    double angka1 = Double.parseDouble(angkaPertama.getText().toString());
                    double angka2 = Double.parseDouble(angkaKedua.getText().toString());
                    double result = angka1 + angka2;
                    tvHasil.setText(Double.toString(result));
                } else {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Mohon masukan angka pertama & kedua",
                            Toast.LENGTH_LONG);
                }
            }
        });
        btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((angkaPertama.getText().length() > 0) && (angkaKedua.getText().length() > 0)) {
                    double angka1 = Double.parseDouble(angkaPertama.getText().toString());
                    double angka2 = Double.parseDouble(angkaKedua.getText().toString());
                    double result = angka1 - angka2;
                    tvHasil.setText(Double.toString(result));
                } else {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Mohon masukan angka pertama & kedua",
                            Toast.LENGTH_LONG);
                }
            }
        });
        btnKali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((angkaPertama.getText().length() > 0) && (angkaKedua.getText().length() > 0)) {
                    double angka1 = Double.parseDouble(angkaPertama.getText().toString());
                    double angka2 = Double.parseDouble(angkaKedua.getText().toString());
                    double result = angka1 * angka2;
                    tvHasil.setText(Double.toString(result));
                } else {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Mohon masukan angka pertama & kedua",
                            Toast.LENGTH_LONG);
                }
            }
        });
        btnBagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((angkaPertama.getText().length() > 0) && (angkaKedua.getText().length() > 0)) {
                    double angka1 = Double.parseDouble(angkaPertama.getText().toString());
                    double angka2 = Double.parseDouble(angkaKedua.getText().toString());
                    double result = angka1 / angka2;
                    tvHasil.setText(Double.toString(result));
                } else {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Mohon masukan angka pertama & kedua",
                            Toast.LENGTH_LONG);
                }
            }
        });
        btnBersihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angkaPertama.setText("");
                angkaKedua.setText("");
                tvHasil.setText("0");
                angkaPertama.requestFocus();
            }
        });
    }
}