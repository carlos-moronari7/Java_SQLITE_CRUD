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
                    Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageString);
                try {
                    long id = crudOperations.insertData(name, age);
                    if (id != -1) {
                        Toast.makeText(MainActivity.this, "Dados inseridos com sucesso com ID: " + id, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Falha ao inserir dados", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Erro ao inserir dados: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Erro ao inserir dados", Toast.LENGTH_SHORT).show();
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
                int id = Integer.parseInt(idEditText.getText().toString());
                Cursor cursor = crudOperations.getDataById(id);
                String name = null;
                int age = -1;
                if (cursor != null && cursor.moveToFirst()) {
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    age = cursor.getInt(cursor.getColumnIndex("age"));
                    cursor.close();
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
                int id = Integer.parseInt(idEditText.getText().toString());
                int rowsDeleted = crudOperations.deleteData(id);
                if (rowsDeleted > 0) {
                    Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
