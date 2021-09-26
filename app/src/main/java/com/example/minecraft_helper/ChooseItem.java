package com.example.minecraft_helper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChooseItem extends AppCompatActivity {

    // components
    ListView listView;
    TextView textView;
    EditText editText;

    // arrays
    private final ArrayList<Integer> listImages = new ArrayList<>();
    private final ArrayList<String> listItemsNames = new ArrayList<>();
    private final ArrayList<String> listTags = new ArrayList<>();

    // adapter
    private CustomListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        listView = findViewById(R.id.lvChoose);
        textView = findViewById(R.id.txtView);
        editText = findViewById(R.id.etSearch);


        this.adapter = new CustomListViewAdapter(this, listItemsNames,
                listImages);

        readItemsJSON();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String value = listTags.get(i);
            Intent intent = new Intent();
            intent.putExtra("value", value);
            intent.putExtra("image", listImages.get(i));
            setResult(RESULT_OK, intent);
            finish();
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    readItemsJSON();
                } else {
                    searchItem(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    private void readItemsJSON(){
        // reading JSON to array
        InputStream is = getResources().openRawResource(R.raw.items);
        Item[] items = new Gson().fromJson(new InputStreamReader(is), Item[].class);

        this.listItemsNames.clear();
        this.listTags.clear();
        this.listImages.clear();

        Context context = getApplicationContext();
        Resources resources = getResources();
        for (Item item : items) {
            this.listItemsNames.add(item.getName());
            this.listTags.add(item.getTag());
            this.listImages.add(resources.getIdentifier(item.getTag(),
                    "drawable", context.getPackageName()));
        }

        adapter.notifyDataSetChanged();
    }


    private void searchItem(String str) {
        for (int i = 0; i < listItemsNames.size(); i++){
            if(!listItemsNames.get(i).toLowerCase()
                .contains(str.toLowerCase())){
                listItemsNames.remove(i);
                listTags.remove(i);
                listImages.remove(i);
            }
        }

        adapter.notifyDataSetChanged();
    }
}