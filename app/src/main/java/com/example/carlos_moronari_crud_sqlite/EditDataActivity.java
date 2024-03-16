package com.example.carlos_moronari_crud_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText;
    private Button updateButton;
    private CRUDOperations crudOperations;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        nameEditText = findViewById(R.id.editNameEditText);
        ageEditText = findViewById(R.id.editAgeEditText);
        updateButton = findViewById(R.id.updateDataButton);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", -1);

        // Preencher os campos com os detalhes passados
        nameEditText.setText(name);
        ageEditText.setText(String.valueOf(age));

        crudOperations = new CRUDOperations(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEditText.getText().toString();
                int newAge = Integer.parseInt(ageEditText.getText().toString()); // Obtain the new age from the text field

                int rowsAffected = crudOperations.updateData(id, newName, newAge); // Update both the name and age
                if (rowsAffected > 0) {
                    Toast.makeText(EditDataActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditDataActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
