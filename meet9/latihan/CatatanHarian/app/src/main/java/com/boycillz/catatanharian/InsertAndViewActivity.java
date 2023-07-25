package com.boycillz.catatanharian;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class InsertAndViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_STORAGE = 100;
    EditText editFilename, editContent;
    Button btnSimpan;
    Toolbar tbInsertView;
    boolean isEditable = false;
    String fileName = "";
    String tempCatatan = "";
    int eventID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_view);
        tbInsertView = findViewById(R.id.tbInsertView);
        editFilename = findViewById(R.id.editFilename);
        editContent = findViewById(R.id.editContent);
        btnSimpan = findViewById(R.id.btnSimpan);

//        setSupportActionBar(tbInsertView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileName = extras.getString("filename");
            editFilename.setText(fileName);
            getSupportActionBar().setTitle("Ubah Catatan");
        } else {
            getSupportActionBar().setTitle("Tambah Catatan");
        }

        eventID = 1;

        if (Build.VERSION.SDK_INT >= 23) {
            if (periksaIzinPeyimpanan()) {
                bacaFile();
            }
        } else {
            bacaFile();
        }

        btnSimpan.setOnClickListener(this);

    }

    public boolean periksaIzinPeyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (eventID == 1) {
                        bacaFile();
                    } else {
                        tampilkanDialogKonfirmasiPeyimpanan();
                    }
                }
                break;
        }
    }

    void bacaFile() {
        String path = Environment.getExternalStorageDirectory().toString() +
                "/com.boycillz.catatanharian";
        File file = new File(path, editFilename.getText().toString());

        if (file.exists()) {
            editFilename.setEnabled(true);
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Error : " + e.getMessage());
            }
            tempCatatan = text.toString();
            editContent.setText(text.toString());
        }
    }

    void buatDanUbah() {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }

        String path = Environment.getExternalStorageDirectory().toString() +
                "/com.boycillz.catatanharian";
        File parent = new File(path);
        if (parent.exists()) {
            File file = new File(path, editFilename.getText().toString());
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                streamWriter.append(editContent.getText());
                streamWriter.flush();
                streamWriter.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            parent.mkdir();
            File file = new File(path, editFilename.getText().toString());

            FileOutputStream outputStream = null;

            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(editContent.getText().toString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onBackPressed();
    }

    public void tampilkanDialogKonfirmasiPeyimpanan() {
        new AlertDialog.Builder(this)
                .setTitle("Simpan Catatan")
                .setMessage("Apakah anda yakkin ingin menyimpan catatan ini ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(InsertAndViewActivity.this, "click simpan",
                                Toast.LENGTH_SHORT).show();
                        buatDanUbah();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!tempCatatan.equals(editContent.getText().toString())) {
            tampilkanDialogKonfirmasiPeyimpanan();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        eventID = 2;
        if (!tempCatatan.equals(editContent.getText().toString())) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (periksaIzinPeyimpanan()) {
                    tampilkanDialogKonfirmasiPeyimpanan();
                }
            } else {
                tampilkanDialogKonfirmasiPeyimpanan();
            }
        }
    }
}