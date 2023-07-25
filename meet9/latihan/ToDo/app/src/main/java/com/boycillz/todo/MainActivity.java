package com.boycillz.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boycillz.todo.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<Todo> todoList;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoList = new ArrayList<>();
        appDatabase = AppDatabase.getInstance(this);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEditTodoActivity(null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTodos();
    }

    private void loadTodos() {
        todoList = appDatabase.todoDao().getAllTodos();
        todoAdapter = new TodoAdapter(todoList, new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                openAddEditTodoActivity(todo);
            }

            @Override
            public void onItemDelete(Todo todo) {
                deleteTodoItem(todo);
            }
        });
        recyclerView.setAdapter(todoAdapter);
    }

    private void openAddEditTodoActivity(Todo todo) {
        Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
        if (todo != null) {
            intent.putExtra("todo_id", todo.id);
        }
        startActivity(intent);
    }

    private void deleteTodoItem(final Todo todo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Todo")
                .setMessage("Are you sure you want to delete this todo?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appDatabase.todoDao().delete(todo);
                        loadTodos();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
