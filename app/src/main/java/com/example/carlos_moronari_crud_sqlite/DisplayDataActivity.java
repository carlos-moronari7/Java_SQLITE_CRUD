package com.example.carlos_moronari_crud_sqlite;


import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayDataActivity extends AppCompatActivity {

    private ListView dataListView;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        dataListView = findViewById(R.id.dataListView);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        dataListView.setAdapter(adapter);

        // Retrieve data from database and display in ListView
        CRUDOperations crudOperations = new CRUDOperations(this);
        Cursor cursor = crudOperations.getAllData();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                dataList.add("ID: "+ id +" | Name: " + name + " | Age: " + age);
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}
