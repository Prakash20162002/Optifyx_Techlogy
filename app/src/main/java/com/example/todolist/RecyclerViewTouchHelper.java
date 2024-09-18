package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Canvas;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.example.todolist.Adapter.todoadapter;
import androidx.core.content.ContextCompat;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback{



     todoadapter adapter;
    public RecyclerViewTouchHelper(todoadapter adapter) {
        super(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position=viewHolder.getBindingAdapterPosition();
        if(direction==ItemTouchHelper.RIGHT)
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("DeleteTask");
            builder.setMessage("Work Done ??");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.deleteitem(position);
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                 adapter.notifyItemChanged(position);
                }
            });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        else
        {


            adapter.eddittask(position);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(ContextCompat.getColor(adapter.getContext(), R.color.my_background)) // Fixed method name
                .addActionIcon(R.drawable.delete) // Fixed method name and syntax
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
