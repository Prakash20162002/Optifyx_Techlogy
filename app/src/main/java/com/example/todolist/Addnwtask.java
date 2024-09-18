package com.example.todolist;
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

import com.example.todolist.Model.TODO_MODEL;
import com.example.todolist.utils.Databasehelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
public class Addnwtask extends BottomSheetDialogFragment {
    public static final  String TAG="AddnewTask";
    private EditText mEdittext;
    private Button mSaveButton;
    private Databasehelper db;

    public static Addnwtask newInstance()
    {
        return new Addnwtask();
    }


   @Nullable
   @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
   {
       View v=inflater.inflate(R.layout.add_new_task,container,false);
       return v;
   }
   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
   {
       super.onViewCreated(view,savedInstanceState);
       mEdittext=view.findViewById(R.id.edittext);
       mSaveButton=view.findViewById(R.id.add_button);

       db=new Databasehelper(getActivity());
       boolean isUpdate =false;

       Bundle bundle=getArguments();
       if(bundle!=null)
       {
           isUpdate=true;
           String task=bundle.getString("task");
           mEdittext.setText(task);
           if(task.length()>0)

           {
               mSaveButton.setEnabled(false);
           }
       }
        mEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                {
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }
                else
                {
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.black));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
       boolean finalIsUpdate = isUpdate;
       mSaveButton.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               String text=mEdittext.getText().toString();
               if(finalIsUpdate)
               {
                   db.updateTask(bundle.getInt("id"),text);
           }
               else {
                   TODO_MODEL item = new TODO_MODEL();
                   item.setTask(text);
                   item.setStatus(0);
                   db.addTask(item);
               }
               dismiss();
           }
       });

       }
       @Override
    public void onDismiss(@NonNull DialogInterface dailog)
       {
           super.onDismiss(dailog);
           Activity activity =getActivity();
           if(activity instanceof OnDailogCloser)
           {
               ((OnDailogCloser)activity).onDailogClose(dailog);
           }



   }

}