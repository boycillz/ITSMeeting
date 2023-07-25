package com.boycillz.loginvalidation;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "login";
    EditText editUsername, editPassword, editEmail, editNamaLengkap, editAsalSekolah, editAlamat;
    Button btnSimpan;
    TextView textViewPassword;
    Toolbar tbRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbRegister = findViewById(R.id.tbRegister);
//        setSupportActionBar(tbRegister);
        getSupportActionBar().setTitle("Halaman Depan");
        editUsername = findViewById(R.id.editUsernameRegister);
        editPassword = findViewById(R.id.editPasswordRegister);
        editEmail = findViewById(R.id.editEmailRegister);
        editNamaLengkap = findViewById(R.id.editNamaLengkapRegister);
        editAsalSekolah = findViewById(R.id.editAsalSekolahRegister);
        editAlamat = findViewById(R.id.editAlamatRegister);
        btnSimpan = findViewById(R.id.btnSimpanRegister);

        btnSimpan.setVisibility(View.GONE);
        editUsername.setEnabled(false);
        editPassword.setVisibility(View.GONE);
//        textViewPassword.setVisibility(View.GONE);
        editEmail.setEnabled(false);
        editNamaLengkap.setEnabled(false);
        editAsalSekolah.setEnabled(false);
        editAlamat.setEnabled(false);
        bacaFileLogin();
    }

    void bacaFileLogin() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);
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
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");
            bacaDataUser(dataUser[0]);
        }
    }

    void bacaDataUser(String fileName) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, fileName);
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
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");
            editUsername.setText(dataUser[0]);
            editEmail.setText(dataUser[2]);
            editNamaLengkap.setText(dataUser[3]);
            editAsalSekolah.setText(dataUser[4]);
            editAlamat.setText(dataUser[5]);
        } else {
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                tampilkanDialogKonfirmasiLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    void tampilkanDialogKonfirmasiLogout() {
        new AlertDialog.Builder(this).setTitle("Logout").setMessage("Apakah Anda yakin ingin Logout?").setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                hapusFile();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton(android.R.string.no, null).show();
    }
}