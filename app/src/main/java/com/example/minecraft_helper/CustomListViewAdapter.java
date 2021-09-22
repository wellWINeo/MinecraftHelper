package com.example.minecraft_helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListViewAdapter extends ArrayAdapter {
    private final String[] itemNames;
    private final Integer[] itemImages;
    private final Activity context;

    public CustomListViewAdapter(Activity context, String[] itemNames,
                                 Integer[] imgsId) {
        super(context, R.layout.item_view, itemNames);
        this.context = context;
        this.itemNames = itemNames;
        this.itemImages = imgsId;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = this.context.getLayoutInflater();
        if (inflater != null) {
            view = inflater.inflate(R.layout.item_view, null, true);
        }

        TextView txtView = view.findViewById(R.id.txtView);
        ImageView imgView = view.findViewById(R.id.imgView);

        txtView.setText(itemNames[position]);
        imgView.setImageResource(itemImages[position]);
        Log.d("image", itemImages[position].toString());


        return view;
    }
}
