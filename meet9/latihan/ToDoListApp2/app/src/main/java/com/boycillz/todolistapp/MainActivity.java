package com.boycillz.todolistapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> todoList;
    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi daftar ToDoList dan RecyclerView
        todoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoAdapter = new ToDoAdapter(todoList);
        recyclerView.setAdapter(toDoAdapter);

        // Handle intent untuk menambahkan data dari SplashScreenActivity
        Intent intent = getIntent();
        if (intent != null) {
            String newToDo = intent.getStringExtra("new_todo");
            if (newToDo != null) {
                // Tambahkan data baru ke ToDoList
                todoList.add(newToDo);
                toDoAdapter.notifyDataSetChanged();
            }
        }

        // Tambahkan fungsi onClick untuk FloatingActionButton
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk ke AddToDoActivity
                Intent intent = new Intent(MainActivity.this, AddToDoActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    // Override method onActivityResult untuk menerima hasil dari AddToDoActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                String newToDo = data.getStringExtra("new_todo");
                if (newToDo != null) {
                    // Tambahkan data baru ke ToDoList
                    todoList.add(newToDo);
                    toDoAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}