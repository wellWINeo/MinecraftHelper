package com.example.minecraft_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseItem extends AppCompatActivity {

    ListView listView;
    TextView textView;
    String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        listView = findViewById(R.id.lvChoose);
        textView = findViewById(R.id.txtView);
        listItems = getResources().getStringArray(R.array.items_array);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String value = adapter.getItem(i);
            //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent().putExtra("chosen", value));
            finish();
        });

    }
}