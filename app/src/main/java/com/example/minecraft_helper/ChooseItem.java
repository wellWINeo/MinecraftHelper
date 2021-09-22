package com.example.minecraft_helper;

import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ChooseItem extends AppCompatActivity {

    ListView listView;
    TextView textView;
    ImageView imgView;
    Integer[] listImages;
    String[] listItemsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        listView = findViewById(R.id.lvChoose);
        textView = findViewById(R.id.txtView);
//        listItemsNames = getResources().getStringArray(R.array.minecraft_name);
//        listItemsId = getResources().getStringArray(R.array.minecraft_id);
//        listImages = getResources().getIntArray(R.array.minecraft_images);

        this.readItemsJSON();

        Log.i("image_array", Arrays.toString(listImages));

        final CustomListViewAdapter adapter = new CustomListViewAdapter(this, listItemsNames,
                listImages);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String value = listItemsNames[i];
            Intent intent = new Intent();
            intent.putExtra("value", value);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void readItemsJSON(){
        // reading JSON to array
        InputStream is = getResources().openRawResource(R.raw.items);
        Item[] items = new Gson().fromJson(new InputStreamReader(is), Item[].class);

        this.listItemsNames = new String[items.length];
        this.listImages = new Integer[items.length];

        Context context = getApplicationContext();
        Resources resources = getResources();
        for (int i = 0; i < items.length; i++){
            this.listItemsNames[i] = items[i].getName();
            this.listImages[i] = resources.getIdentifier(items[i].getTag(),
                    "drawable", context.getPackageName());
        }
    }
}