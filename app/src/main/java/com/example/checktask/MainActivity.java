package com.example.checktask;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checktask.Adapter.TaskAdapter;
import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.example.checktask.itemTouch.ItemTouchEvent;
import com.example.checktask.itemTouch.SwapGesturesItemTouch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener, ItemTouchEvent {
    static MainActivity mainActivity;
    TaskAdapter adapter;
    private DataBaseHandle toDoDataBase;
    private List<TaskModel> taskModelList;

    public static MainActivity getInstance() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainActivity = this;
        RecyclerView recyclerView_item = findViewById(R.id.Main_task_list_id);
        FloatingActionButton floatingActionButton_add_Task = findViewById(R.id.add_task_fab);

        toDoDataBase = new DataBaseHandle(this);
        toDoDataBase.openDatabase();

        taskModelList = new ArrayList<>();
        adapter = new TaskAdapter(toDoDataBase, MainActivity.this, this);
        recyclerView_item.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwapGesturesItemTouch(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView_item);

        recyclerView_item.setHasFixedSize(true);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this));

        taskModelList = toDoDataBase.getAllTasks();
        Collections.reverse(taskModelList);
        adapter.setTaskList(taskModelList);


        floatingActionButton_add_Task.setOnClickListener(view -> AddNewItemsBottomSheetDialog.newInstance().show(getSupportFragmentManager(), AddNewItemsBottomSheetDialog.TAG));


    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        taskModelList = toDoDataBase.getAllTasks();
        Collections.reverse(taskModelList);
        adapter.setTaskList(taskModelList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position, String task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("task", task);
        startActivity(intent);
    }
}