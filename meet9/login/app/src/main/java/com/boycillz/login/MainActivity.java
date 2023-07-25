package com.boycillz.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail, etNama, etSekolah, etAlamat;
    Button btnRegister;
    TextView textView;

    public static final String FILENAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Halaman depan");

        etUsername = findViewById(R.id.etUsername);
        textView = findViewById(R.id.tvPassword);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etNama = findViewById(R.id.etNamaLengkap);
        etSekolah = findViewById(R.id.etSekolah);
        etAlamat = findViewById(R.id.etAlamat);
        btnRegister = findViewById(R.id.btnRegister);

        //menyembunyikan, mendisabledkan item
        etUsername.setEnabled(false);
        textView.setVisibility(View.GONE);
        etPassword.setVisibility(View.GONE);
        etEmail.setEnabled(false);
        etNama.setEnabled(false);
        etSekolah.setEnabled(false);
        etAlamat.setEnabled(false);
        btnRegister.setVisibility(View.GONE);

        bacaFileLogin();
    }

    void bacaDataUser(String username) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, username);

        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe.getMessage());
            }

            String data = text.toString();
            String[] detailUser = data.split(";");

            etUsername.setText(detailUser[0]);
            etEmail.setText(detailUser[2]);
            etNama.setText(detailUser[3]);
            etSekolah.setText(detailUser[4]);
            etAlamat.setText(detailUser[5]);
        }
    }

    void bacaFileLogin() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe.getMessage());
            }

            String data = text.toString();
            String [] dataUser = data.split(";");
            bacaDataUser(dataUser[0]);
        }
    }

    void hapusFile()
    {
        File file = new File(getFilesDir(), FILENAME);
        file.delete();
    }

    void tampilkanDialogKonfirmasiLogout()
    {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hapusFile();
                        Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.itemLogout:
                tampilkanDialogKonfirmasiLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

