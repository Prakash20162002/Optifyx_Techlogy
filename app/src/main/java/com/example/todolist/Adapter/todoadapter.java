package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Addnwtask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.TODO_MODEL;
import com.example.todolist.R;
import com.example.todolist.utils.Databasehelper;

import java.util.List;

public class todoadapter extends RecyclerView.Adapter<todoadapter.Myviewholder> {
    private List<TODO_MODEL> mlist;
    private MainActivity activity;
    private Databasehelper db;

    // Constructor
    public todoadapter(Databasehelper db, MainActivity activity, List<TODO_MODEL> mlist) {
        this.activity = activity;
        this.db = db;
        this.mlist = mlist;  // Initialize mlist
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final TODO_MODEL item = mlist.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));

        // Listener for checkbox status change
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                db.updateTask(item.getId(), String.valueOf(1));  // Task completed
            } else {
                db.updateTask(item.getId(), String.valueOf(0));  // Task not completed
            }
        });
    }

    // Helper method to convert integer status to boolean
    public boolean toBoolean(int n) {
        return n != 0;
    }

    public Context getContext() {
        return activity;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setTasks(List<TODO_MODEL> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    public void deleteitem(int position)
    {
        TODO_MODEL item=mlist.get(position);
        db.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }
    public void eddittask(int position)
    {
        TODO_MODEL item=mlist.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        Addnwtask task=new Addnwtask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }

    public static class Myviewholder extends RecyclerView.ViewHolder {
        CheckBox checkBox;  // Correct spelling

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.chcek_box);  // Correct spelling in layout
        }
    }
}
