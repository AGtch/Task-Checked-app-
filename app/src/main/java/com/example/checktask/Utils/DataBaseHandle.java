package com.example.checktask.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.checktask.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

final class TaskDataBaseContract implements BaseColumns {
    private TaskDataBaseContract(){
        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
    }
        public static final String tableName = "task_table";
        public static final String ID = "id";
        public static final String Task = "task";
        public static final String IsDone = "is_done";
        public static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE "+ tableName +
                "("+ ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + Task + "TEXT,"
                   + IsDone + "BLOB"+
                ");" ;
        public static final String SQL_QUERY_DELETE_TABLE = "DROP TABLE IF EXISTS "+ tableName;
}



public class DataBaseHandle extends SQLiteOpenHelper   { //

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taskManagement.db";
    public  SQLiteDatabase DataBase ;
    public  TaskModel taskModel ;
    public DataBaseHandle(Context context) {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TaskDataBaseContract.SQL_QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TaskDataBaseContract.SQL_QUERY_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        super.onDowngrade(sqLiteDatabase, oldVersion, newVersion);
    }
    public void addNewTask(String newTask){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDataBaseContract.Task , taskModel.getTasks());
        contentValues.put(TaskDataBaseContract.IsDone , taskModel.getStatus());
        sqLiteDatabase.insert(TaskDataBaseContract.tableName , null , contentValues);
        sqLiteDatabase.close();
    }
    public void openDataBase(){
        DataBase = getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<TaskModel> getAllTask(){
        Cursor cursor = null;
        List<TaskModel> item_list = new ArrayList<>();
        openDataBase();
        DataBase.beginTransaction();
        try {
            cursor = DataBase.query(TaskDataBaseContract.tableName ,
                    null,
                    null,
                    null ,
                    null,
                    null ,
                    null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TaskModel tasks = new TaskModel();
                        tasks.setId(cursor.getColumnIndex(TaskDataBaseContract.ID));
                        tasks.setTasks(cursor.getString(cursor.getColumnIndex(TaskDataBaseContract.Task)));
                        tasks.setStatus(cursor.getInt(cursor.getColumnIndex(TaskDataBaseContract.IsDone)));
                        item_list.add(tasks);
                    } while (cursor.moveToNext());
                }
            }
        }finally {
            DataBase.endTransaction();
            DataBase.close();
        }
        return item_list ;
    }
}
