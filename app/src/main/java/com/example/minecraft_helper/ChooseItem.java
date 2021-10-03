package com.example.minecraft_helper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class ChooseItem extends AppCompatActivity {

    // components
    ListView listView;
    TextView textView;
    EditText editText;

    // arrays
    private ArrayList<Item> itemList = new ArrayList<>();

    // adapter
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        listView = findViewById(R.id.lvChoose);
        textView = findViewById(R.id.txtView);
        editText = findViewById(R.id.etSearch);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String value = ((Item) adapter.getItem(i)).getName();
            Intent intent = new Intent();
            intent.putExtra("value", value);
            intent.putExtra("image", ((Item) adapter.getItem(i)).getImage());
            setResult(RESULT_OK, intent);
            finish();
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        readItemsJSON();

        adapter = new SearchAdapter(this, itemList);
        listView.setAdapter(adapter);
    }

    private void readItemsJSON(){
        // reading JSON to array
        InputStream is = getResources().openRawResource(R.raw.items);
        itemList = new Gson().fromJson(new InputStreamReader(is),
                new TypeToken<ArrayList<Item>>(){}.getType());

        for (Item item : itemList) {
            int imgId = getResources().getIdentifier(item.getTag(),
                    "drawable", getApplicationContext().getPackageName());
            item.setImage(imgId);
        }

        Log.d("Array_count", String.valueOf(itemList.size()));

//        adapter.notifyDataSetChanged();
    }

}


