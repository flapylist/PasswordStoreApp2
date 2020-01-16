package com.example.passwordstoreapp2;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private PasswordRecyclerAdapter1 adapter;

    private ColorDrawable background;

    public SwipeToDeleteCallback(PasswordRecyclerAdapter1 adapter) {
        super(0, ItemTouchHelper.RIGHT);
        this.adapter = adapter;

        background=new ColorDrawable(ContextCompat.getColor(adapter.getContext(),R.color.deleteColor));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int derection) {
        int position = holder.getAdapterPosition();
        EventBus.getDefault().post(new DeleteEvent(position));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backGroundCornerOffset = 15;



        if (dX > 0) {


            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backGroundCornerOffset,
                    itemView.getBottom());
        } else background.setBounds(0,0,0,0);

        background.draw(c);

    }


}
