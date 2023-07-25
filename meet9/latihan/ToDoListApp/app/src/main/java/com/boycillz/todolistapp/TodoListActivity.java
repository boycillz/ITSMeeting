package com.boycillz.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.boycillz.todolistapp.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TodoListActivity extends AppCompatActivity {

    private TextView textViewDateTime;
    private EditText editTextTodo;
    private Button buttonAddTodo;
    private ListView listViewTodos;

    private ArrayList<ToDoItem> todoItems;
    private ArrayAdapter<ToDoItem> todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        textViewDateTime = findViewById(R.id.textViewDateTime);
        editTextTodo = findViewById(R.id.editTextTodo);
        buttonAddTodo = findViewById(R.id.buttonAddTodo);
        listViewTodos = findViewById(R.id.listViewTodos);

        todoItems = new ArrayList<>();
        todoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
        listViewTodos.setAdapter(todoAdapter);

        buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });

        listViewTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTodoStatus(position);
            }
        });

        listViewTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTodoItem(position);
                return true;
            }
        });

        // Menampilkan tanggal dan jam saat memasukkan data
        updateDateTime();
    }

    private void addTodo() {
        String todoTitle = editTextTodo.getText().toString().trim();
        if (!todoTitle.isEmpty()) {
            ToDoItem todoItem = new ToDoItem(todoTitle);
            todoItems.add(todoItem);
            todoAdapter.notifyDataSetChanged();
            editTextTodo.setText(""); // Menghapus isi EditText setelah menambahkan ToDo
        }
    }

    private void editTodoStatus(int position) {
        ToDoItem todoItem = todoItems.get(position);
        todoItem.setDone(!todoItem.isDone());
        todoAdapter.notifyDataSetChanged();
    }

    private void deleteTodoItem(int position) {
        todoItems.remove(position);
        todoAdapter.notifyDataSetChanged();
    }

    private void updateDateTime() {
        String dateFormat = "dd MMMM yyyy";
        String timeFormat = "HH:mm";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat, Locale.getDefault());

        String date = dateFormatter.format(new Date());
        String time = timeFormatter.format(new Date());

        textViewDateTime.setText("Tanggal: " + date + ", Jam: " + time);
    }
}