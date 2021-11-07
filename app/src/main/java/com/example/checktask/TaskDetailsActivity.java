package com.example.checktask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checktask.Adapter.TaskAdapter;
import com.example.checktask.itemTouch.Operations;

public class TaskDetailsActivity extends AppCompatActivity implements View.OnClickListener, Operations {
    static Intent intent;
    TextView textViewDisplayTask;
    ImageButton btnGoBack, btnDeleteTask, btnStartWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        textViewDisplayTask = findViewById(R.id.txtview_showTask_id);
        btnDeleteTask = findViewById(R.id.btn_delete);
        btnGoBack = findViewById(R.id.bt_back_id);
        btnStartWork = findViewById(R.id.btn_start_work);


        intent = getIntent();
        textViewDisplayTask.setText(intent.getStringExtra("task"));
        btnDeleteTask.setOnClickListener(this);
        btnGoBack.setOnClickListener(this);
        btnStartWork.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete: {
                delete(intent.getIntExtra("position", -1), "Delete Task!", "Do you want to delete Task");
                break;
            }
            case R.id.bt_back_id: {
                onBackPressed();
                finish();
                break;
            }
            case R.id.btn_start_work:
                Toast.makeText(getApplicationContext(), "Start it ", Toast.LENGTH_SHORT).show();
                Intent startFoucIntent  = new Intent(this , PromodoroActivity.class);
                startActivity(startFoucIntent);
        }
    }


    @Override
    public void delete(int position, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            TaskAdapter.newInstanceOfAdapter().deleteTask(position);
            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> TaskAdapter.newInstanceOfAdapter().notifyItemChanged(position));
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}