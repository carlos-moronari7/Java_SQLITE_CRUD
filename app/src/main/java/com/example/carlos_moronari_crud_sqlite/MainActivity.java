package com.example.carlos_moronari_crud_sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.carlos_moronari_crud_sqlite.CRUDOperations;
import com.example.carlos_moronari_crud_sqlite.DisplayDataActivity;
import com.example.carlos_moronari_crud_sqlite.EditDataActivity;
import com.example.carlos_moronari_crud_sqlite.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText, idEditText;
    private Button addButton, viewButton, updateButton, deleteButton;
    private CRUDOperations crudOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        idEditText = findViewById(R.id.idEditText);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);


        crudOperations = new CRUDOperations(this);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String ageString = ageEditText.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageString)) {
                    Toast.makeText(MainActivity.this, "Please, fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageString);
                try {
                    long id = crudOperations.insertData(name, age);
                    if (id != -1) {
                        Toast.makeText(MainActivity.this, "Data insert with ID: " + id, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to insert Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Failed to insert data " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = idEditText.getText().toString().trim();
                if (idText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                    idEditText.setError("You must enter an ID");
                    idEditText.requestFocus();
                    return; // Exit the onClick method to prevent further execution
                }

                int id = 0;
                String name = null;
                int age = 0;
                try {
                    id = Integer.parseInt(idText);
                    Cursor cursor = crudOperations.getDataById(id);
                    if (cursor != null && cursor.moveToFirst()) {
                        // Record with the provided ID exists, proceed with update
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        age = cursor.getInt(cursor.getColumnIndex("age"));
                        // Proceed with your update logic here
                    } else {
                        // Record with the provided ID does not exist
                        Toast.makeText(MainActivity.this, "ID does not exist", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the ID text cannot be parsed into an integer
                    Toast.makeText(MainActivity.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Print the exception details to the console for debugging
                }

                Intent intent = new Intent(MainActivity.this, EditDataActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = idEditText.getText().toString().trim();
                if (idText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                    idEditText.setError("You must enter an ID");
                    idEditText.requestFocus();
                    return; // Exit the onClick method to prevent further execution
                }
                try {
                    int id = Integer.parseInt(idText);
                    int rowsDeleted = crudOperations.deleteData(id);
                    if (rowsDeleted > 0) {
                        Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Check if the ID does not exist in the database
                        if (!crudOperations.checkIfIdExists(id)) {
                            Toast.makeText(MainActivity.this, "ID does not exist", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to delete data", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the ID text cannot be parsed into an integer
                    Toast.makeText(MainActivity.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Print the exception details to the console for debugging
                }
            }
        });
    }
}
