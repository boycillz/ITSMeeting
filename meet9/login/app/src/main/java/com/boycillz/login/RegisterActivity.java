package com.boycillz.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etUsername, etPassword, etNama, etSekolah, etAlamat, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Register");

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etAlamat = findViewById(R.id.etAlamat);
        etSekolah = findViewById(R.id.etSekolah);
        etNama = findViewById(R.id.etNamaLengkap);
        etEmail = findViewById(R.id.etEmail);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidation()) {
                    simpanFileData();
                } else {
                    Toast.makeText(RegisterActivity.this, "Mohon lengkapi data",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

boolean isValidation() {
    if(etUsername.getText().toString().equals("") ||
            etPassword.getText().toString().equals("") ||
            etAlamat.getText().toString().equals("") ||
            etSekolah.getText().toString().equals("") ||
            etNama.getText().toString().equals("") ||
            etEmail.getText().toString().equals("")) {
        return false;
    } else {
        return true;
    }
}

    void simpanFileData() {
        String isiFile = etUsername.getText().toString() + ";" +
                etPassword.getText().toString() + ";" +
                etAlamat.getText().toString() + ";" +
                etSekolah.getText().toString() + ";" +
                etNama.getText().toString() + ";" +
                etEmail.getText().toString();
        File file = new File(getFilesDir(),
                etUsername.getText().toString());
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Register Berhasil",
                Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

}