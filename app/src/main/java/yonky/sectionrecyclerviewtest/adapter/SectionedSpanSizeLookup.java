package yonky.sectionrecyclerviewtest.adapter;

import android.support.v7.widget.GridLayoutManager;

public class SectionedSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    protected SectionedRecyclerViewAdapter<?,?,?,?,?> adapter = null;
    protected GridLayoutManager layoutManager=null;
    public SectionedSpanSizeLookup(SectionedRecyclerViewAdapter<?,?,?,?,?> adapter,GridLayoutManager gridLayoutManager){
        this.adapter = adapter;
        this.layoutManager = gridLayoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        if(adapter.hasHeader()) {
            if (position == 0) {
                return layoutManager.getSpanCount();
            } else if (position < adapter.getItemCount() - 1) {
                if (adapter.isSectionHeaderPosition(position - 1) || adapter.isSectionFooterPosition(position - 1)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            } else {
                return layoutManager.getSpanCount();
            }
        }else{
            if(position<adapter.getItemCount()-1){
                    if(adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition(position)){
                        return layoutManager.getSpanCount();
                    }else {
                        return 1;
                    }
            }else {
                    return layoutManager.getSpanCount();
            }
        }

    }
}
