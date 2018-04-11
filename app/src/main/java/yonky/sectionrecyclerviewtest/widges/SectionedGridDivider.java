package yonky.sectionrecyclerviewtest.widges;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import yonky.sectionrecyclerviewtest.adapter.SectionedRecyclerViewAdapter;

public class SectionedGridDivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mDividerHeight = 1;
    private Paint mDividerPaint;

    public SectionedGridDivider(Context context, int drawableId){
        this.mDividerDrawable = ContextCompat.getDrawable(context,drawableId);
        mDividerHeight=mDividerDrawable.getIntrinsicHeight();
    }

    public SectionedGridDivider(Context context,int mDividerHeight,int dividerColor){
        this.mDividerHeight=mDividerHeight;
        mDividerPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(dividerColor);
        mDividerPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int totalCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if(isDraw(parent,view,totalCount)){
            if(itemPosition==0){
                outRect.set(0,0,0,0);
            }else{
                outRect.set(0,mDividerHeight,0,0);
            }
        }
    }
    private boolean isDraw(RecyclerView parent,View itemView,int totalCount){
        int itemPosition= parent.getChildAdapterPosition(itemView);
        if(totalCount>1 && itemPosition<totalCount-1){
            if(parent.getAdapter() instanceof SectionedRecyclerViewAdapter){
                if(((SectionedRecyclerViewAdapter)parent.getAdapter()).isSectionHeaderPosition(itemPosition)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c,parent);
    }

    private void drawHorizontal(Canvas c,RecyclerView parent){
        int totalCount  =parent.getAdapter().getItemCount();
//        获取当前课件的item数量，半个也算
        int childCount =parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            if(isDraw(parent,child,totalCount)){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                int left=child.getLeft()-params.leftMargin;
                int right = child.getRight()+params.rightMargin;
                int top=child.getTop()-mDividerHeight-params.bottomMargin;
                int bottom=top+mDividerHeight;
                if(mDividerDrawable!=null){
                    mDividerDrawable.setBounds(left,top,right,bottom);
                    mDividerDrawable.draw(c);
                }
                if(mDividerPaint!=null){
                    c.drawRect(left,top,right,bottom,mDividerPaint);
                }
            }
        }
    }
}
