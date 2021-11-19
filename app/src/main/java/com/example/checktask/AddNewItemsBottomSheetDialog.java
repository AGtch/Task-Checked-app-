package com.example.checktask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewItemsBottomSheetDialog extends BottomSheetDialogFragment {
    public static String TAG = "AddNewItemsBottomSheetDialog";

    private EditText enter_Task_editText, inputTxtDescription, inputTxtDate, inputTxtTime;
    private DataBaseHandle myDataBase;


    public static AddNewItemsBottomSheetDialog newInstance() {
        return new AddNewItemsBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        enter_Task_editText = view.findViewById(R.id.enter_task_input);
        inputTxtDescription = view.findViewById(R.id.input_task_description);
        inputTxtDate = view.findViewById(R.id.input_task_date);
        inputTxtTime = view.findViewById(R.id.input_task_time);

        Button submit_Task_ImageButton = view.findViewById(R.id.submit_btn);
        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            enter_Task_editText.setText(task);
        }
        myDataBase = new DataBaseHandle(getActivity());
        myDataBase.openDatabase();

        final boolean finalIsUpdate = isUpdate;

        enter_Task_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().equals("")) {
                    submit_Task_ImageButton.setEnabled(false);
                    submit_Task_ImageButton.setBackgroundColor(R.color.gray);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    submit_Task_ImageButton.setEnabled(false);
                    submit_Task_ImageButton.setBackgroundColor(Color.GRAY);
                } else {
                    submit_Task_ImageButton.setEnabled(true);
                    submit_Task_ImageButton.setBackgroundColor(R.style.addBtnStyle);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().length() > 0) {
                    submit_Task_ImageButton.setOnClickListener(view1 -> {
                        String taskString = Objects.requireNonNull(enter_Task_editText.getText()).toString();
                        if (finalIsUpdate) {
                            myDataBase.updateTask(bundle.getInt("id"), taskString);
                        } else {
                            TaskModel item = new TaskModel();
                            item.setTasks(taskString);
                            item.setStatus(0);
                            myDataBase.insertTask(item);
                        }
                        dismiss();
                    });
                }
            }
        });
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
