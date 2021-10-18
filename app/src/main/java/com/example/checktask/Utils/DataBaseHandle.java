package com.example.checktask.Utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.checktask.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Ali Ahmed Gomaa
 * @version 1.0
 */


public class DataBaseHandle extends SQLiteOpenHelper   {

    /* Inner class that defines the table contents
     * And save variables from bad using or errors by calling
     */
    static class DataBaseContract implements BaseColumns {
        private static final int VERSION = 1;
        private static final String DATABASE_NAME = "taskManagementDataBase";
        private static final String TODO_TABLE = "todolistTable";
        private static final String ID = "id";
        private static final String TASK = "task";
        private static final String STATUS = "status";
        private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
                + STATUS + " INTEGER)";
    }

    private SQLiteDatabase db;

    public DataBaseHandle(Context context) {
        super(context, DataBaseContract.DATABASE_NAME, null, DataBaseContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseContract.CREATE_TODO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.TODO_TABLE);
        // Create tables again
        onCreate(db);
    }
    // Open DataBase to write in
    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    // save model in database it is require task as String get it from user input view ,and status must be 0
    public void insertTask(TaskModel task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.TASK, task.getTasks());
        contentValues.put(DataBaseContract.STATUS, 0);
        db.insert(DataBaseContract.TODO_TABLE, null, contentValues);
        db.close();
    }

    // this method to show data from database on RecyclerView
    @SuppressLint("Range")
    public List<TaskModel> getAllTasks(){
        List<TaskModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(DataBaseContract.TODO_TABLE
                    ,null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null);

            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(DataBaseContract.ID)));
                        task.setTasks(cursor.getString(cursor.getColumnIndex(DataBaseContract.TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContract.STATUS)));
                        taskList.add(task);
                    }
                    while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return taskList; // list of items in DataBase
    }

    public void updateStatus(int id, int status){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.STATUS, status);
        db.update(DataBaseContract.TODO_TABLE, contentValues, DataBaseContract.ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.TASK, task);
        db.update(DataBaseContract.TODO_TABLE, contentValues , DataBaseContract.ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(DataBaseContract.TODO_TABLE, DataBaseContract.ID + "= ?", new String[] {String.valueOf(id)});
    }

}
