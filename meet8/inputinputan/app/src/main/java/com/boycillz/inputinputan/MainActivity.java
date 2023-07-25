package com.boycillz.inputinputan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonShowName;
    private TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextName = findViewById(R.id.editTextName);
        buttonShowName = findViewById(R.id.buttonShowName);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        buttonShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                if (!name.isEmpty()) {
                    textViewDisplay.setText("Nama: " + name);
                    textViewDisplay.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}