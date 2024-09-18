package com.example.todolist.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.Model.TODO_MODEL;

import java.util.ArrayList;
import java.util.List;

public class Databasehelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COLUMN_1 = "ID";    // Fixed capitalization
    private static final String COLUMN_2 = "TASK";
    private static final String COLUMN_3 = "STATUS";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the table if it doesn't exist
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Add a new task
    public void addTask(TODO_MODEL model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, model.getTask());  // Set task value
        contentValues.put(COLUMN_3, 0);                // Default status is 0 (incomplete)

        // Insert into the database
        db.insert(TABLE_NAME, null, contentValues);
    }

    // Update the task name based on task ID
    public void updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, task);  // Set the new task text

        // Update the database where ID matches
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
    }

    // Update the task status based on task ID
    public void updateTaskStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_3, status);  // Set the new status

        // Update the status where ID matches
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
    }

    // Delete a task based on its ID
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
    }

    // Get all tasks from the database
    @SuppressLint("Range")
    public List<TODO_MODEL> getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<TODO_MODEL> todoList = new ArrayList<>();
        Cursor cursor = null;

        // Start a database transaction
        db.beginTransaction();
        try {
            // Query the database for all rows in the table
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

            // If cursor is not null and there is at least one row
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TODO_MODEL todoModel = new TODO_MODEL();
                    todoModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_1)));      // Get task ID
                    todoModel.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_2)));  // Get task text
                    todoModel.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_3)));  // Get task status

                    todoList.add(todoModel);  // Add to list
                } while (cursor.moveToNext());  // Move to the next row
            }

            db.setTransactionSuccessful();  // Mark the transaction as successful
        } finally {
            db.endTransaction();  // End the transaction

            // Close cursor if it's not null
            if (cursor != null) {
                cursor.close();
            }
        }

        return todoList;  // Return the list of tasks
    }
}
