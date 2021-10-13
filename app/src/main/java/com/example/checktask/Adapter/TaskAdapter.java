package com.example.checktask.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checktask.AddNewItemsBottomSheetDialog;
import com.example.checktask.MainActivity;
import com.example.checktask.Model.TaskModel;
import com.example.checktask.R;
import com.example.checktask.Utils.DataBaseHandle;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskModel> taskList ;
    private final MainActivity mainActivity ;
    private DataBaseHandle myDataBase ;

    public TaskAdapter(DataBaseHandle myDataBase , MainActivity mainActivity  ){
            this.myDataBase = myDataBase;
            this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data from TaskMode class to checkBox ( View of RecyclerView )
        myDataBase.openDatabase();

        final  TaskModel item = taskList.get(position);
        holder.checkBox.setText(item.getTasks());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    myDataBase.updateStatus(item.getId() , 1);
                }else myDataBase.updateStatus(item.getId() , 0);
            }
        });

    }

    public Context getContext(){
        return mainActivity;
    }
    // check if Status of task is Done Or Not (1 ,0 )
    public boolean toBoolean(int number){
        return number != 0;
    }


    public void setTaskList(List<TaskModel> taskList){
        this.taskList = taskList;
        notifyDataSetChanged() ;
    }

    public void deleteTask(int position){

        TaskModel itemToDelete = taskList.get(position);
        myDataBase.deleteTask(itemToDelete.getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }


    public void editItem(int position){

        TaskModel item = taskList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTasks());
        AddNewItemsBottomSheetDialog FragmentTask = new AddNewItemsBottomSheetDialog();
        FragmentTask.setArguments(bundle);
        FragmentTask.show(mainActivity.getSupportFragmentManager(), AddNewItemsBottomSheetDialog.TAG);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox ;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.your_task);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
