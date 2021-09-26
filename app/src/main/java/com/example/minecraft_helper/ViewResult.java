package com.example.minecraft_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewResult extends AppCompatActivity {

    Bundle bundle;

    // components
    ImageView imgView;
    TextView txtView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        imgView = findViewById(R.id.imgResult);
        txtView = findViewById(R.id.txtResult);
        btnBack = findViewById(R.id.btnResultBack);

        Intent intent =  getIntent();
        bundle = intent.getExtras();

        String title = bundle.getString("title");
        String imageName = bundle.getString("image");
        int imgId = getResources().getIdentifier(imageName, "drawable",
                getApplicationContext().getPackageName());

        txtView.setText(title);
        imgView.setImageResource(imgId);

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}