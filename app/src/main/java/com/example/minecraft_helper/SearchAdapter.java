package com.example.minecraft_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Item> itemOriginList;
    private ArrayList<Item> itemDisplayedList;
    LayoutInflater inflater;
    Context context;

    public SearchAdapter(Context context, ArrayList<Item> itemList){
        this.itemOriginList = itemList;
        this.itemDisplayedList = itemList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.itemDisplayedList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemDisplayedList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        GridLayout glContainer;
        TextView txtView;
        ImageView imgView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_view, null);
            holder.glContainer = view.findViewById(R.id.glContainer);
            holder.imgView = view.findViewById(R.id.imgView);
            holder.txtView = view.findViewById(R.id.txtView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtView.setText(this.itemDisplayedList.get(i).getName());
        holder.imgView.setImageResource(this.itemDisplayedList.get(i).getImage());

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<Item> filteredList = new ArrayList<>();

                if (itemOriginList == null){
                    itemOriginList = new ArrayList<>(itemDisplayedList);
                }

                if (charSequence == null || charSequence.length() == 0){
                    results.count = itemOriginList.size();
                    results.values = itemOriginList;
                } else {
                    charSequence = charSequence.toString().toLowerCase();

                    for (int i = 0; i < itemOriginList.size(); i++){
                        String data = itemOriginList.get(i).getName();
                        if (data.toLowerCase().contains(charSequence)){
                            filteredList.add(itemOriginList.get(i));
                        }
                    }

                    results.count = filteredList.size();
                    results.values = filteredList;
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemDisplayedList = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
