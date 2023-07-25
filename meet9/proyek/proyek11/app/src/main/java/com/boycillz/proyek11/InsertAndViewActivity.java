package com.boycillz.proyek11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class InsertAndViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_STORAGE = 100;
    int eventID = 0;
    EditText etFileName, etContent;
    Button btnSimpan;
    boolean isEditable = false;
    String fileName = "";
    String tempCatatan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etFileName = findViewById(R.id.etFileName);
        etContent = findViewById(R.id.etContent);
        btnSimpan = findViewById(R.id.btnSimpan);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            fileName = extras.getString("filename");
            etFileName.setText(fileName);
            getSupportActionBar().setTitle("Ubah Catatan");
        } else {
            getSupportActionBar().setTitle("Tambah Catatan");
        }
        eventID = 1;
        if (Build.VERSION.SDK_INT >= 23){
            if (periksaIzinPenyimpanan()){
                bacaFile();
            }
        } else {
            bacaFile();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSimpan:
                eventID = 2;
                if (!tempCatatan.equals(etContent.getText()
                        .toString())) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (periksaIzinPenyimpanan()) {
                            tampilkanDialougKonfirmasiPenyimpanan();
                        }
                    } else {
                        tampilkanDialougKonfirmasiPenyimpanan();
                    }
                }
                break;
        }
    }

    public boolean periksaIzinPenyimpanan(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
             == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE:
                if (grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                    if (eventID == 1){
                        bacaFile();
                    } else {
                        tampilkanDialougKonfirmasiPenyimpanan();
                    }
                }
                break;
        }
    }

    void bacaFile(){
        String path = Environment.getExternalStorageDirectory()
                .toString() + "/kominfo.proyek1";
        File file = new File(path, etFileName.getText().toString());
        if (file.exists()){
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (Exception e){
                System.out.println("Error "+e.getMessage());
            }
            tempCatatan = text.toString();
            etContent.setText(text.toString());
        }
    }

    void buatDanUbah(){
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)){
            return;
        }
        String path = Environment.getExternalStorageDirectory().
                toString()+"/kominfo.proyek1";
        File parent = new File(path);
        if (parent.exists()){
            File file = new File(path, etFileName.getText().toString());
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file);
                OutputStreamWriter streamWriter = new
                        OutputStreamWriter(outputStream);
                streamWriter.append(etContent.getText());
                streamWriter.flush();
                streamWriter.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            parent.mkdir();
            File file = new File(path, etFileName.getText().toString());
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(etContent.getText().toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        onBackPressed();
    }

    void tampilkanDialougKonfirmasiPenyimpanan(){
        new AlertDialog.Builder(this)
                .setTitle("Simpan Catatan")
                .setMessage("Apakah anda yakin menyimpan catatan?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buatDanUbah();
                            }
                        })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
        if (!tempCatatan.equals(etContent.getText().toString())){
            tampilkanDialougKonfirmasiPenyimpanan();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}