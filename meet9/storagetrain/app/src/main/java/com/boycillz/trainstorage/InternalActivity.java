package com.boycillz.trainstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FILENAME = "namafile.txt";
    Button buatFile, ubahFile, bacaFile, hapusFile;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        buatFile = findViewById(R.id.btnBuatFile);
        ubahFile = findViewById(R.id.btnUbahFile);
        bacaFile = findViewById(R.id.btnUbahFile);
        hapusFile = findViewById(R.id.btnHapusFile);
        textView = findViewById(R.id.tvBaca);

        buatFile.setOnClickListener(this);
        ubahFile.setOnClickListener(this);
        bacaFile.setOnClickListener(this);
        hapusFile.setOnClickListener(this);
    }

    void buatFile() {
        String isiFile = "Coba Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ubahFile() {
        String ubah = "update isi data file text";

        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;

        try {
            file.createNewFile();
            outputStream.write(ubah.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void bacaFile() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

        if (file.exists()) {
            StringBuilder builder = new StringBuilder();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line = reader.readLine();

                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
            textView.setText(builder.toString());
        }
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void onClick(View v) {
        jalankanPerintah(v.getId());
    }

    public void jalankanPerintah(int id) {
        switch (id) {
            case R.id.btnBuatFile:
                buatFile();
                break;
            case R.id.btnUbahFile:
                ubahFile();
                break;
            case R.id.btnBacaFile:
                bacaFile();
                break;
            case R.id.btnHapusFile:
                hapusFile();
                break;
        }
    }
}