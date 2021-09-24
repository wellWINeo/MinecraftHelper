package com.example.minecraft_helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainActivity extends AppCompatActivity {

    Button btn;
    MaterialButton[][] grid;
    int pressedButton;

    Map<String, String> receipts = null;



    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && intent != null) {
                    Bundle bundle = intent.getExtras();
                    MaterialButton button = findViewById(this.pressedButton);
                    button.setIconResource(bundle.getInt("image", R.drawable.air));
                    button.setContentDescription(bundle.getString("value"));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();

        btn = findViewById(R.id.btnViewItem);
        btn.setOnClickListener(this::viewItemButtonClick);


    }

    private void viewItemButtonClick(View view){
        String comb = "";
        for(MaterialButton[] btnRow : grid){
            for(MaterialButton button : btnRow){
                comb += button.getContentDescription() + "+";
            }
        }
        comb = comb.substring(0, comb.length() - 1);

        Toast.makeText(getApplicationContext(), comb, Toast.LENGTH_LONG).show();

        String item = this.receipts.get(comb);

        if (item == null)
            item = "Not Found";

//        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
    }

    private void readRawJSON(){
        InputStream inputStream = getResources().openRawResource(R.raw.receipts);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.receipts = gson.<Map<String, String>>fromJson(jsonString, Map.class);

        Log.i("receipts", receipts.toString());
    }

    private void init(){

        new Thread(this::readRawJSON).start();

        this.grid = new MaterialButton[][]{
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

        for(MaterialButton[] btnRow : grid){
            for(MaterialButton button : btnRow){
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