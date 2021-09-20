package com.example.minecraft_helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button[][] grid;
    int pressedButton;

    // for custom ListView



    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && intent != null) {
                    Bundle bundle = intent.getExtras();
                    Button button = findViewById(this.pressedButton);
                    button.setText(bundle.getString("value"));
                }
            });

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
                    this.pressedButton = view.getId();
                    //startActivity(intent);
                    mStartForResult.launch(intent);
                });
            }
        }
    }
}