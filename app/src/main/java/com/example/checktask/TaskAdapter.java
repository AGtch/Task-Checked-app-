package com.example.checktask;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    List<TaskModel> taskList ;
    MainActivity mainActivity ;
    DataBaseHandle myDataBase ;


    public TaskAdapter(DataBaseHandle myDataBase , MainActivity mainActivity ,List<TaskModel> taskList ){
            this.myDataBase = myDataBase;
            this.mainActivity = mainActivity;
            this.taskList = taskList;
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
        final  TaskModel item = taskList.get(position);
        holder.checkBox.setText(item.getTasks());
        holder.checkBox.setChecked(isChecked(item.getStatus()));
    }

    public boolean isChecked(byte num){
        return num != 0;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setTaskList(List<TaskModel> taskList){
        this.taskList = taskList;
        notifyDataSetChanged() ;
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox= itemView.findViewById(R.id.your_task);
        }
        public CheckBox getCheckBox(){
            return checkBox;
        }
    }
}
