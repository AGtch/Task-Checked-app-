package com.example.checktask;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checktask.Adapter.TaskAdapter;
import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.example.checktask.itemTouch.SwapGesturesItemTouch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {
    private DataBaseHandle toDoDataBase ;

    private RecyclerView recyclerView_item ;
    private FloatingActionButton  floatingActionButton_add_Task ;
    private List<TaskModel> taskModelList ;
    private TaskAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_item = findViewById(R.id.Main_task_list_id);
        floatingActionButton_add_Task = findViewById(R.id.add_task_fab);
        toDoDataBase = new DataBaseHandle(this);
        toDoDataBase.openDatabase();

        taskModelList = new ArrayList<>();
        adapter = new TaskAdapter( toDoDataBase ,MainActivity.this );
        recyclerView_item.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwapGesturesItemTouch(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView_item);

        recyclerView_item.setHasFixedSize(true);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this));

        taskModelList = toDoDataBase.getAllTasks();
        Collections.reverse(taskModelList);
        adapter.setTaskList(taskModelList);
        Log.d("Taglist", taskModelList.toString());
        floatingActionButton_add_Task.setOnClickListener(view -> AddNewItemsBottomSheetDialog.newInstance().show(getSupportFragmentManager() , AddNewItemsBottomSheetDialog.TAG));



    }


    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        taskModelList = toDoDataBase.getAllTasks();
        Collections.reverse(taskModelList);
        adapter.setTaskList(taskModelList);
        adapter.notifyDataSetChanged();
    }
}