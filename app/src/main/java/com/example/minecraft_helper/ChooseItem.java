package com.example.minecraft_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseItem extends AppCompatActivity {

    ListView listView;
    TextView textView;
    ImageView imgView;
    String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        listView = findViewById(R.id.lvChoose);
        textView = findViewById(R.id.txtView);
        listItems = getResources().getStringArray(R.array.items_array);
        Integer[] listImages = {
                R.drawable.earth_block,
                R.drawable.earth_block,
                R.drawable.earth_block,
                R.drawable.earth_block,
                R.drawable.earth_block
        };

        final CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                listItems, listImages);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String value = listItems[i];
            //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("value", value);
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}