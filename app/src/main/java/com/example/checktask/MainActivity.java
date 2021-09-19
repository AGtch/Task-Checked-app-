package com.example.checktask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView_item ;
    TextInputEditText inputEditText ;
    List<TaskModel> taskModelList ;
    FloatingActionButton  floatingActionButton_add_Task ;
    private DataBaseHandle toDoDataBase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView_item = findViewById(R.id.Main_task_list_id);
        floatingActionButton_add_Task = findViewById(R.id.add_task_fab);
        floatingActionButton_add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     Toast.makeText(getApplicationContext() , "floating btn is work" , Toast.LENGTH_SHORT).show();
                AddNewItemsBottomSheetDialog.newInstance().show(getSupportFragmentManager() , AddNewItemsBottomSheetDialog.TAG);

            }
        });

    }
    public void displayTask(){
        taskModelList = new ArrayList<>(toDoDataBase.getAllTask());
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_item.setItemAnimator(new DefaultItemAnimator());
        TaskAdapter adapter = new TaskAdapter(toDoDataBase , this , taskModelList);
        recyclerView_item.setAdapter(adapter);
    }
}