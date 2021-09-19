package com.example.minecraft_helper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button[][] grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();

        btn = findViewById(R.id.btnViewItem);
        btn.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "placeholder", Toast.LENGTH_SHORT).show();
        });

    }

    private void init(){
        this.grid = new Button[][]{
                // row 0
                { findViewById(R.id.btn_0_0),
                  findViewById(R.id.btn_0_1),
                  findViewById(R.id.btn_0_2), },

                // row 1
                { findViewById(R.id.btn_1_0),
                  findViewById(R.id.btn_1_1),
                  findViewById(R.id.btn_1_2), },

                // row 2
                { findViewById(R.id.btn_2_0),
                  findViewById(R.id.btn_2_1),
                  findViewById(R.id.btn_2_2), }
        };

        for(Button[] btnRow : grid){
            for(Button button : btnRow){
                button.setOnClickListener(view -> {
                    Intent intent = new Intent(MainActivity.this, ChooseItem.class);
                    startActivity(intent);
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            String value = bundle.getString("chosen");
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
        }
    }
}