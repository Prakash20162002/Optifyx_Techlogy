package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.todoadapter;
import com.example.todolist.Model.TODO_MODEL;
import com.example.todolist.utils.Databasehelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDailogCloser
{
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    Databasehelper db;
    private List<TODO_MODEL> mlist;
    private todoadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.iteam);
        add_button=findViewById(R.id.add_button);
        db=new Databasehelper(MainActivity.this);
        mlist=new ArrayList<>();
        adapter=new todoadapter(db,MainActivity.this,mlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mlist=db.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);


        add_button.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Addnwtask.newInstance().show(getSupportFragmentManager(),Addnwtask.TAG);


            }
        });
        ItemTouchHelper intemtouchhelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        intemtouchhelper.attachToRecyclerView(recyclerView);
        }

    @Override
    public void onDailogClose(DialogInterface dialog) {
        mlist=db.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        adapter.notifyDataSetChanged();

    }
}