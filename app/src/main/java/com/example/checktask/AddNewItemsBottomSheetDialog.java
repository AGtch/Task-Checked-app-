package com.example.checktask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.checktask.Model.TaskModel;
import com.example.checktask.Utils.DataBaseHandle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class AddNewItemsBottomSheetDialog extends BottomSheetDialogFragment {
    public static String TAG = "AddNewItemsBottomSheetDialog";

    private EditText enter_Task_editText, inputTxtDescription, inputTxtDate, inputTxtTime;
    private DataBaseHandle myDataBase;
    Calendar calendar = Calendar.getInstance();
    int mYear, mMonth, mDay;
    int mHour, mMinute;

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


        inputTxtDate.setEnabled(true);
        inputTxtDate.setTextIsSelectable(true);
        inputTxtDate.setFocusable(false);
        inputTxtDate.setFocusableInTouchMode(false);

        inputTxtTime.setEnabled(true);
        inputTxtTime.setTextIsSelectable(true);
        inputTxtTime.setFocusable(false);
        inputTxtTime.setFocusableInTouchMode(false);

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
                        String taskDate = Objects.requireNonNull(inputTxtDate.getText()).toString();
                        if (finalIsUpdate) {
                            myDataBase.updateTask(bundle.getInt("id"), taskString);
                        } else {
                            TaskModel item = new TaskModel();
                            item.setTasks(taskString);
                            item.setStatus(0);
                            item.setDescription("de");
                            item.setDate(taskDate);
                            item.setTime("ti");
                            myDataBase.insertTask(item);
                        }
                        dismiss();
                    });
                }
            }
        });


        inputTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog();
            }
        });

        inputTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog();
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

    public void datePickerDialog() {
        // Get Current Date
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                inputTxtDate.setText(dayOfMonth + "-" + (month + 1));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void timePickerDialog() {
        // Get Current Time

        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Toast.makeText(getContext(), String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
                String status = "AM";

                // Initialize a new variable to hold 12 hour format hour value
                int hour_of_12_hour_format;
                String formatMinute;
                if (hourOfDay > 11) {
                    status = "PM";
                    // If the hour is greater than or equal to 12
                    // Then we subtract 12 from the hour to make it 12 hour format time
                    hour_of_12_hour_format = hourOfDay - 12;
                } else {
                    hour_of_12_hour_format = hourOfDay;
                }
                if (minute <= 9) {
                    formatMinute = String.valueOf("0" + minute);
                } else {
                    formatMinute = String.valueOf(minute);
                }
                inputTxtTime.setText(hour_of_12_hour_format + ":" + formatMinute + " " + status);
                dismiss();
            }
        }, mMinute, mHour, false);
        timePickerDialog.show();
    }
}
