package com.boycillz.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        final EditText editText = findViewById(R.id.edit_text);
        Button buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newToDo = editText.getText().toString();
                if (!newToDo.isEmpty()) {
                    // Kirim data baru ke MainActivity
                    Intent intent = new Intent();
                    intent.putExtra("new_todo", newToDo);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
