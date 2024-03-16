package com.example.carlos_moronari_crud_sqlite;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
    // CustomAdapter.java


    public class CustomAdapter extends ArrayAdapter<String> {

        private final Typeface typeface;

        public CustomAdapter(Context context, List<String> objects, Typeface typeface) {
            super(context, 0, objects);
            this.typeface = typeface;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
            textView.setTypeface(typeface);

            return convertView;
        }
    }

