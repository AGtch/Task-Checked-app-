package com.example.checktask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {
    RecyclerView recyclerView_item ;
    FloatingActionButton  floatingActionButton_add_Task ;
    private DataBaseHandle toDoDataBase ;
    private List<TaskModel> taskModelList ;
    TaskAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView_item = findViewById(R.id.Main_task_list_id);
        floatingActionButton_add_Task = findViewById(R.id.add_task_fab);
        toDoDataBase = new DataBaseHandle(MainActivity.this);
        taskModelList = new ArrayList<>();
        adapter = new TaskAdapter( toDoDataBase ,MainActivity.this );

        recyclerView_item.setHasFixedSize(true);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_item.setAdapter(adapter);
        displayItemsInRecyclerView();

        floatingActionButton_add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewItemsBottomSheetDialog.newInstance().show(getSupportFragmentManager() , AddNewItemsBottomSheetDialog.TAG);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        displayItemsInRecyclerView();
        adapter.notifyDataSetChanged();
    }
    void displayItemsInRecyclerView(){
        taskModelList = toDoDataBase.getAllTask();
        Collections.reverse(taskModelList);
        adapter.setTaskList(taskModelList);
    }
}