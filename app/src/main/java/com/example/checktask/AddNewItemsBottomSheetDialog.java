package com.example.checktask;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItemsBottomSheetDialog extends BottomSheetDialogFragment {
    public static String TAG = "AddNewItemsBottomSheetDialog";

    private EditText enter_Task_editText ;
    private Button submit_Task_ImageButton;
    private DataBaseHandle myDataBase ;


    public static AddNewItemsBottomSheetDialog newInstance(){
        return new AddNewItemsBottomSheetDialog();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task , container , false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enter_Task_editText = view.findViewById(R.id.enter_task_edittext);
        submit_Task_ImageButton = view.findViewById(R.id.submit_btn);

        boolean isUpdate = false ;
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate= true;
            String task = bundle.getString("task");
            enter_Task_editText.setText(task);

        }
        myDataBase = new DataBaseHandle(getActivity());
        myDataBase.openDatabase();

        final boolean finalIsUpdate = isUpdate;

        submit_Task_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskString = enter_Task_editText.getText().toString();

                if (finalIsUpdate){
                    myDataBase.updateTask(bundle.getInt("id") , taskString);
                }else {

                    TaskModel item = new TaskModel();
                    item.setTasks(taskString);
                    item.setStatus(0);
                    myDataBase.insertTask(item);
                }
                dismiss();
            }
        });

    }




    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
