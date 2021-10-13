package com.example.checktask.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.checktask.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Ali Ahmed Gomaa
 * @version 1.0
 */
//// This class is Base Contract which contain the data variables which use in DataBase Querie
//class TaskDataBaseContract {
//
//    private TaskDataBaseContract() {
//        // To prevent someone from accidentally instantiating the contract class,
//        // make the constructor private.
//    }
//
//    public static final String TABLENAME = "task_table";
//    public static final String ID = "id";
//    public static final String TASK = "task";
//    public static final String IsDone = "is_done"; //  Equal to status TaskModel.class
//
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "taskManagementSystem.db";
//
//    public static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLENAME +
//            "(" + ID + "INTEGER PRIMARY KEY AUTOINCREMENT ,"
//            + TASK + "TEXT,"
//            + IsDone + "INTEGER" +
//            ");";
//    public static final String SQL_QUERY_DELETE_TABLE = "DROP TABLE " + TABLENAME;
//
//}


public class DataBaseHandle extends SQLiteOpenHelper   {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "taskManagementDataBase";
    private static final String TODO_TABLE = "todolistTable";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBaseHandle(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(TaskModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTasks());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<TaskModel> getAllTasks(){
        List<TaskModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTasks(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

}
