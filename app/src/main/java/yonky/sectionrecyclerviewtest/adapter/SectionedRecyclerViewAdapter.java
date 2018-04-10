package yonky.sectionrecyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2018/4/10.
 */

public abstract class SectionedRecyclerViewAdapter extends Adapter {

//    分组的Header
    protected static final int TYPE_SECTION_HEADER = -1;
//    分组的Footer
    protected static final int TYPE_SECTION_FOOTER = -2;
//    分组的内容
    protected  static final int TYPE_ITEM = -3;
//    整个列表的Header
    protected static final int TYPE_HEADER = 0;
//    整个列表的Footer
    protected static final int TYPE_FOOTER = 1;
//    上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
public static final int LOADING_MORE = 1;

    //加载完成
    public static final int LOADING_FINISH = 2;

    public static final int TYPE_EMPTY = -4;

    //上拉加载默认状态--默认为-1
    public int load_more_status = -1;

    //用来保存分组section位置
    private int[] sectionForPosition = null;

    //用来保存分组内的每项的position位置
    private int[] positionWithinSection = null;

    //用来记录每个位置是否是一个组内Header
    private boolean[] isHeader = null;

    //用来记录每个位置是否是一个组内Footer
    private boolean[] isFooter = null;

    //item的总数，注意，是总数，包含所有项
    private int count = 0;

//以下接口对应各个item的点击事件
public OnChildClickListener onChildClickListener;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;
    public OnSectionHeaderClickListener onSectionHeaderClickListener;
    public OnSectionFooterClickListener onSectionFooterClickListener;

    private View emptyView;
    private boolean emptyViewVisible;

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public SectionedRecyclerViewAdapter(){
        super();
        //RecyclerView采用观察者(Observer)模式，对外提供了registerDataSetObserver和unregisterDataSetObserver
        //两个方法，用来监控数据集的变化
        registerAdapterDataObserver(new SectionDataObserver());
    }

    class SectionDataObserver extends RecyclerView.AdapterDataObserver{
        @Override
        public void onChanged() {
            setupPosition();
            checkEmpty();
        }
    }

    private void setupPosition(){
        count = countItem();
        setupArrays(count);
        calculatePositions();
    }

    private int countItem(){
        int count = 0;
        int sections = getSectionCount();
        for(int i= 0;i<sections;i++){
            count+=1+getItemCountForSection(i)+(hasFooterInSection(i)? 1:0);
        }
        return count;
    }







    /**
     * 分组内的item点击回调
     */
    public interface OnItemClickListener {
        void onItemClick(int section, int position);
    }

    /**
     * item长按回调
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(int section, int position);
    }

    /**
     * section的Header的点击回调
     */
    public interface OnSectionHeaderClickListener {
        void onSectionHeaderClick(int section);
    }

    /**
     * section的Footer的点击回调
     */
    public interface OnSectionFooterClickListener {
        void onSectionFooterClick(int section);
    }

    /**
     * 分组内子View点击事件回调，多了一个viewType，用以区分同一个item的不同的点击事件
     * 根据需求，需要时可实现此接口
     */
    public interface OnChildClickListener{
        void onChildClick(int position,int viewType);
}

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

}
