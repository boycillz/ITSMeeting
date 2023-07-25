package com.boycillz.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.boycillz.todo.model.Todo;

public class AddEditTodoActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private Button btnSave;
    private AppDatabase appDatabase;
    private TodoDao todoDao;
    private int todoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_todo);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        appDatabase = AppDatabase.getInstance(this);
        todoDao = appDatabase.todoDao();

        if (getIntent().hasExtra("todo_id")) {
            todoId = getIntent().getIntExtra("todo_id", -1);
        }

        if (todoId != -1) {
            loadTodoData();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTodo();
            }
        });
    }

    private void loadTodoData() {
        Todo todo = todoDao.getTodoById(todoId);
        if (todo != null) {
            etTitle.setText(todo.title);
            etDescription.setText(todo.description);
        }
    }

    private void saveTodo() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Title cannot be empty");
            etTitle.requestFocus();
            return;
        }

        if (todoId == -1) {
            // Tambah Todo baru
            Todo newTodo = new Todo();
            newTodo.title = title;
            newTodo.description = description;
            newTodo.isCompleted = false;
            todoDao.insert(newTodo);
        } else {
            // Update Todo
            Todo todo = todoDao.getTodoById(todoId);
            if (todo != null) {
                todo.title = title;
                todo.description = description;
                todoDao.update(todo);
            }
        }

        finish();
    }
}
