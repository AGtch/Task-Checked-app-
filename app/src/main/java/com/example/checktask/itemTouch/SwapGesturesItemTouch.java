package com.example.checktask.itemTouch;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checktask.Adapter.TaskAdapter;


public class SwapGesturesItemTouch extends ItemTouchHelper.SimpleCallback implements Operations {

    private final TaskAdapter adapter;

    public SwapGesturesItemTouch(TaskAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        String message = "Do you want to delete this task ?";
        String title = "Good Job";


        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT) {
            delete(position, title, message);
        } else {
            adapter.editItem(position);
        }
    }

    //method to ask if he want to delete task when user swap to delete it
    @Override
    public void delete(int position, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", (dialog, which) -> adapter.deleteTask(position));
        builder.setNegativeButton("Cancel", (dialog, which) -> adapter.notifyItemChanged(position));
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
