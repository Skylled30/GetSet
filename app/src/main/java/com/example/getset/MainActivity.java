package com.example.getset;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText textName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textName = findViewById(R.id.editTextName);
        btnStart = findViewById(R.id.startButton);
    }

    public void onClick(View v){
        if (v.getId() == R.id.startButton) {
            Intent intent = new Intent(this, GameActivity.class);
            if (!textName.getText().toString().equals("")) {
                intent.putExtra("name", textName.getText().toString());
                startActivity(intent);
            }
        }
    }
}
