package com.example.checktask.Utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.checktask.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Ahmed Gomaa
 * @version 1.0
 */


public class DataBaseHandle extends SQLiteOpenHelper {

    private static final String TAG = "sizeofList";
    private SQLiteDatabase db;

    public DataBaseHandle(Context context) {

        super(context, DataBaseContract.DATABASE_NAME, null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(DataBaseContract.CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        // If you need to add a column
       db.execSQL("DETACH DATABASE "+DataBaseContract.DATABASE_NAME);
       db.execSQL(DataBaseContract.DATABASE_ALTER_DATE );
       db.execSQL(DataBaseContract.DATABASE_ALTER_DESCRIPTION );
       db.execSQL(DataBaseContract.DATABASE_ALTER_TIME );


    }

    // Open DataBase to write in
    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    // save model in database it is require task as String get it from user input view ,and status must be 0
    public void insertTask(TaskModel task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.TASK, task.getTasks());
        contentValues.put(DataBaseContract.STATUS, 0);
        contentValues.put(DataBaseContract.DESCRIPTION, task.getDescription());
        contentValues.put(DataBaseContract.DATE, task.getDate());
        contentValues.put(DataBaseContract.TIME, task.getTime());

        db.insert(DataBaseContract.TODO_TABLE, null, contentValues);
        db.close();
    }

    // this method to show data from database on RecyclerView
    @SuppressLint("Range")
    public List<TaskModel> getAllTasks() {
        List<TaskModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(DataBaseContract.TODO_TABLE
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(DataBaseContract.ID)));
                        task.setTasks(cursor.getString(cursor.getColumnIndex(DataBaseContract.TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContract.STATUS)));

                        task.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseContract.DESCRIPTION)));
                        task.setDate(cursor.getString(cursor.getColumnIndex(DataBaseContract.DATE)));
                        task.setTime(cursor.getString(cursor.getColumnIndex(DataBaseContract.TIME)));

                        taskList.add(task);
                    }
                    while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        Log.d(TAG, "getAllTasks: " + taskList.size());
        return taskList; // list of items in DataBase
    }

    // Statues of task (checked or not)
    public void updateStatus(int id, int status) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.STATUS, status);
        db.update(DataBaseContract.TODO_TABLE, contentValues, DataBaseContract.ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.TASK, task);
        db.update(DataBaseContract.TODO_TABLE, contentValues, DataBaseContract.ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(DataBaseContract.TODO_TABLE, DataBaseContract.ID + "= ?", new String[]{String.valueOf(id)});
    }

    /* Inner class that defines the table contents
     * And save variables from bad using or errors by calling
     */
    public static class DataBaseContract implements BaseColumns {
        private static final String DATABASE_NAME = "dataBaseTodo";
        private static final String TODO_TABLE = "todolistTable";
        private static final String ID = "id";
        private static final String TASK = "task";
        private static final String STATUS = "status";

        private static final String DESCRIPTION = "description";
        private static final String DATE = "date";
        private static final String TIME = "time";
        private static final String INSERT = "INSERT INTO " + DataBaseContract.TODO_TABLE +
                "(" + DataBaseContract.DESCRIPTION + "," +
                DataBaseContract.STATUS
                + "," + DataBaseContract.DATE + "," +
                DataBaseContract.TASK + "," +
                DataBaseContract.TIME
                +");";

        private static final String DATABASE_ALTER_DATE =
                "ALTER TABLE " + DataBaseContract.TODO_TABLE + " ADD COLUMN " + DataBaseContract.DATE + " TEXT;";
        private static final String DATABASE_ALTER_DESCRIPTION =
                "ALTER TABLE " + DataBaseContract.TODO_TABLE + " ADD COLUMN " + DataBaseContract.DESCRIPTION + " TEXT;";
        private static final String DATABASE_ALTER_TIME =
                "ALTER TABLE " + DataBaseContract.TODO_TABLE + " ADD COLUMN " + DataBaseContract.TIME + " TEXT;";
        //private static final String insertNewValues = " INSERT INTO todolistTable(description,status,date,task,time) VALUES (NULL,NULL,NULL,NULL , NULL ) ";
        private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK + " TEXT, "
                + STATUS + " INTEGER)";
    }

}
