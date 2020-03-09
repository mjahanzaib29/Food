//package com.example.food.Helper;
//
//import android.graphics.Canvas;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.food.Adapter.Ticket_Adapter;
//
//public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
//    private RecyclerItemTouchHelperListerner listerner;
//    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListerner listerner) {
//        super(dragDirs, swipeDirs);
//        this.listerner = listerner;
//    }
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        return true;
//    }
//
//    @Override
//    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//        if (listerner !=null)
//            listerner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
//    }
//
//    @Override
//    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
////        super.clearView(recyclerView, viewHolder);
//        View foregroundView = ((Ticket_Adapter.MyViewHolder)viewHolder).itemView;
//        getDefaultUIUtil().clearView(foregroundView);
//    }
//
//    @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
////        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        View foregroundView = ((Ticket_Adapter.MyViewHolder)viewHolder).itemView;
//        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
//    }
//
//    @Override
//    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
////        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        View foregroundView = ((Ticket_Adapter.MyViewHolder)viewHolder).itemView;
//        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
//    }
//}
