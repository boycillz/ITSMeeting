package com.boycillz.proyek1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class InsertAndViewActivity extends AppCompatActivity {

    EditText editFilename;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_view);

        editFilename = findViewById(R.id.editFilename);
        editContent = findViewById(R.id.editContent);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String fileName = extras.getString("filename");
            editFilename.setText(fileName);
            getSupportActionBar().setTitle("Ubah Catatan");
        } else {
            getSupportActionBar().setTitle("Tambah Catatan");
        }
        bacaFile();
    }

    void bacaFile() {
        File path = getDir("NOTES", MODE_PRIVATE);
        File file = new File(path, editFilename
                .getText().toString());
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br =
                        new BufferedReader(
                                new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            editContent.setText(text.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    public void saveData(View view) {
        buatDanUbah();
    }

    void buatDanUbah() {
        File path = getDir("NOTES", MODE_PRIVATE);
        File file = new File(path.toString(),
                editFilename.getText().toString());
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file,
                    false);
            outputStream.write(editContent.getText()
                    .toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onBackPressed();
    }
}