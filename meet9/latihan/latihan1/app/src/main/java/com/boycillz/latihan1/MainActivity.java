package com.boycillz.latihan1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FILENAME = "latihanstorage.txt";
    Button createFile, editFile, readFile, deleteFile;
    EditText tvRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFile = findViewById(R.id.btnCreateFile);
        editFile = findViewById(R.id.btnEditFile);
        readFile = findViewById(R.id.btnReadFile);
        deleteFile = findViewById(R.id.btnDeleteFile);
        tvRead = findViewById(R.id.tvRead);

        createFile.setOnClickListener(this);
        editFile.setOnClickListener(this);
        readFile.setOnClickListener(this);
        deleteFile.setOnClickListener(this);


    }


    void createFile() {
        String createFile = "Coba Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(createFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void editFile() {
        String edit = "Update data Text";
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(edit.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {

        }

    }

    void readFile() {
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
            tvRead.setText(text.toString());
        }
    }

    void deleteFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    public void onClick(View v) {
        jalankanPerintah(v.getId());
    }

    public void jalankanPerintah(int id) {
        switch (id) {
            case R.id.btnCreateFile:
                createFile();
                break;
            case R.id.btnEditFile:
                editFile();
                break;
            case R.id.btnReadFile:
                readFile();
                break;
            case R.id.btnDeleteFile:
                deleteFile();
                break;
        }
    }

}