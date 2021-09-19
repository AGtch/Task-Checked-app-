package com.example.checktask;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItemsBottomSheetDialog extends BottomSheetDialogFragment {
    EditText Enter_Task_editText ;
    ImageButton Submit_Task_ImageButton;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Enter_Task_editText = view.findViewById(R.id.enter_task_edittext);
        Submit_Task_ImageButton = view.findViewById(R.id.submit_btn);

    }

    public static AddNewItemsBottomSheetDialog newInstance(){
        return new AddNewItemsBottomSheetDialog();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task , container , false);
        return view;
    }
    public static String TAG = "AddNewItemsBottomSheetDialog";

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

    }
}
