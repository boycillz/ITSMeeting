package com.boycillz.noted;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutTask;
    private TextInputEditText editTextTask;
    private Button buttonAdd;
    private TextView textViewDateTime;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayoutTask = findViewById(R.id.textInputLayoutTask);
        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTask.getText().toString().trim();
                String description = ""; // Get the description from the user
                String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                if (!title.isEmpty()) {
                    TaskModel task = new TaskModel(title, description, dateTime);
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                    editTextTask.setText("");
                    textViewDateTime.setText(dateTime);
                }
            }
        });
    }
}