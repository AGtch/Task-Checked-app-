package com.example.checktask.Utils;

import static com.example.checktask.Utils.TaskDataBaseContract.ID;
import static com.example.checktask.Utils.TaskDataBaseContract.IsDone;
import static com.example.checktask.Utils.TaskDataBaseContract.Task;
import static com.example.checktask.Utils.TaskDataBaseContract.tableName;

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
 *
 */
// This class is Base Contract which contain the data variables which use in DataBase Querie
class TaskDataBaseContract implements BaseColumns {

    private TaskDataBaseContract(){
        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
    }
        public static final String tableName = "task_table";
        public static final String ID = "id";
        public static final String Task = "task";
        public static final String IsDone = "is_done"; //  Equal to status TaskModel.class

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "taskManagement.db";
      ;
        public static final String SQL_QUERY_CREATE_TABLE = " CREATE TABLE "+ tableName +
                "("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Task + " TEXT,"
                + IsDone + " INTEGER"+
                ");" ;
        public static final String SQL_QUERY_DELETE_TABLE = "DROP TABLE IF EXISTS "+ tableName;

}



public class DataBaseHandle extends SQLiteOpenHelper   {

    public  SQLiteDatabase sqlDataBase ;
    public  TaskModel taskModel;
    public DataBaseHandle(Context context) {
        super(context, TaskDataBaseContract.DATABASE_NAME , null , TaskDataBaseContract.DATABASE_VERSION);
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


    public void addNewTask(TaskModel taskModel){
        sqlDataBase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDataBaseContract.Task , taskModel.getTasks());
        contentValues.put(TaskDataBaseContract.IsDone , taskModel.getStatus());
        sqlDataBase.insert(TaskDataBaseContract.tableName , null , contentValues);
        sqlDataBase.close();
    }
    //Method to upDate Task
    public void upDateTask(int id_ofTaskToBeChanged , String newTask){
        sqlDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task , newTask);
        sqlDataBase.update(tableName
                , contentValues
                , ID + "= ?"
                , new String[] {String.valueOf(id_ofTaskToBeChanged)});

    }

//    // this method depended on sqlDataBase Variable and Open DataBase to Write data
//    public void openDataBaseToWriteIN(){
//        sqlDataBase = getWritableDatabase();
//    }

    @SuppressLint({"Range", "Recycle"})
    public List<TaskModel> getAllTask(){
        sqlDataBase = this.getWritableDatabase();

        Cursor cursor = null;
        List<TaskModel> item_list = new ArrayList<>();
        sqlDataBase.beginTransaction();
        try {
            cursor = sqlDataBase.query(tableName ,
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
                        tasks.setId(cursor.getColumnIndex(ID));
                        tasks.setTasks(cursor.getString(cursor.getColumnIndex(Task)));
                        tasks.setStatus(cursor.getInt(cursor.getColumnIndex(IsDone)));
                        item_list.add(tasks);
                    } while (cursor.moveToNext());
                }
            }
        }finally {
            sqlDataBase.endTransaction();
            sqlDataBase.close();
        }
        return item_list ;
    }

    //Update status of Task Done or Not
    public void updateStatus(int id, int status){
        sqlDataBase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(IsDone , status);
        sqlDataBase.update(tableName, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    //Method to Delete task form dataBase
    public void deleteTask(int id_ofTaskToBeDelete){
        sqlDataBase = this.getWritableDatabase();

        sqlDataBase.delete(tableName
                            , ID + "= ?"
                            , new String[] {String.valueOf(id_ofTaskToBeDelete)});

    }
}
