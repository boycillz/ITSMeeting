package com.boycillz.proyek1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_STORAGE = 100;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aplikasi Catatan Sederhana");

        listView = findViewById(R.id.lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        InsertAndViewActivity.class);
                Map<String, Object> data = (Map<String, Object>)
                        parent.getAdapter().getItem(position);
                intent.putExtra("filename", data.get("name").toString());
            }

            public boolean periksaIzinPenyimpanan(){
                if (Build.VERSION.SDK_INT >= 23){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                        return true;
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]
                        (Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE_STORAGE);
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });
    }
}