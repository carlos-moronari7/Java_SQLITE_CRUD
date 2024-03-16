package com.example.carlos_moronari_crud_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CRUDOperations {

    private final DatabaseHelper dbHelper;

    public CRUDOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Método para inserir dados no banco de dados
    public long insertData(String name, int age) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        long id = db.insert("table_user", null, values);
        db.close();
        return id;
    }

    // Método para obter todos os dados da tabela
    public Cursor getAllData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("table_user", null, null, null, null, null, null);
    }

    // Método para obter dados por ID
    public Cursor getDataById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("table_user", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    // Método para atualizar dados na tabela
    public int updateData(int id, String name, int age) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        int rowsAffected = db.update("table_user", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    // Método para excluir dados da tabela
    public int deleteData(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("table_user", "id=?", new String[]{String.valueOf(id)});
    }
}
