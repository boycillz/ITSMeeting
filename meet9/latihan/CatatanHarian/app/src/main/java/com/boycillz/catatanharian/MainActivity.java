package com.boycillz.catatanharian;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_STORAGE = 100;

    Toolbar tbToolbar;
    ListView lvListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbToolbar = findViewById(R.id.tbToolbar);
        lvListview = findViewById(R.id.lvListview);

//        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Aplikasi Catatan Proyek 1");

        lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, InsertAndViewActivity.class);

                Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                intent.putExtra("filename", data.get("name").toString());

                Toast.makeText(MainActivity.this, "You Clicked" + data.get("name"),
                        Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        lvListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
//                Toast.makeText(MainActivity.this, data.get("name").toString(), Toast.LENGTH_SHORT).show();
                tampilkanDialogKonfirmasiHapus(data.get("name").toString());
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (periksaIzinPeyimpanan()) {
                mengambilListPadaFolder();
            }
        } else {
            mengambilListPadaFolder();
        }
    }

    public boolean periksaIzinPeyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
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
                    mengambilListPadaFolder();
                }
                break;
        }
    }

    void mengambilListPadaFolder() {
        String path = Environment.getExternalStorageDirectory().toString() +
                "/com.boycillz.catatanharian";
        System.out.println(path);
        File directory = new File(path);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            String[] fileName = new String[files.length];
            String[] dateCreated = new String[files.length];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

            ArrayList<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < files.length; i++) {
                fileName[i] = files[i].getName();
                Date lastModDate = new Date(files[i].lastModified());
                dateCreated[i] = simpleDateFormat.format(lastModDate);
                Map<String, Object> listItemMap = new HashMap<>();

                listItemMap.put("name", fileName[i]);
                listItemMap.put("date", dateCreated[i]);
                itemDataList.add(listItemMap);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemDataList,
                    android.R.layout.simple_list_item_2, new String[]{"name", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            lvListview.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //15
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            // 15
            case R.id.action_tambah:
                Intent intent = new Intent(this, InsertAndViewActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void tampilkanDialogKonfirmasiHapus(final String filename) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus catatan ini>")
                .setMessage("Apakah anda yakkin ingin menghapus catatan " + filename + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hapusFile(filename);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    void hapusFile(String filename) {
        String path = Environment.getExternalStorageDirectory().toString() +
                "/com.boycillz.catatanharian";
        System.out.println("path " + path);

        File file = new File(path, filename);
        if (file.exists()) {
            file.delete();
        }

        mengambilListPadaFolder();
    }

}