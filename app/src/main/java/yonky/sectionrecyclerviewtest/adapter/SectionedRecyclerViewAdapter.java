package yonky.sectionrecyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import yonky.sectionrecyclerviewtest.holder.EmptyViewHolder;
import yonky.sectionrecyclerviewtest.holder.FooterHolder;
import yonky.sectionrecyclerviewtest.holder.TestSectionHeaderHolder;

/**
 * Created by Administrator on 2018/4/10.
 */

public abstract class SectionedRecyclerViewAdapter<RH extends RecyclerView.ViewHolder,
        H extends RecyclerView.ViewHolder,
        VH extends RecyclerView.ViewHolder,
        F extends RecyclerView.ViewHolder,
        FO extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
            checkEmpty();//检查数据是否为空，设置空布局
        }
        @Override
        public void onItemRangeInserted(int psoitionStart,int itemCount){
            checkEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart,int itemCount){
            checkEmpty();
        }
    }

    private void checkEmpty(){
        if(emptyView !=null){
            if(hasHeader()){
                emptyViewVisible= getItemCount()==2;
            }else{
                emptyViewVisible=getItemCount()==1;
            }
            emptyView.setVisibility(emptyViewVisible? View.VISIBLE:View.GONE);
        }
    }


    /**
     * 返回item总数（包含顶部Header、底部Footer、分组hader和分组footer以及分组item内容）
     */
    @Override
    public int getItemCount(){
        if(hasHeader()){
            return count+2;
        }else{
            return count+1;
        }
    }

    private void setupPosition(){
        count = countItems();
        setupArrays(count);
        calculatePositions();
    }

    /**
     * 计算item的总数量
     *
     * @return
     */
    private int countItems(){
        int count = 0;
        int sections = getSectionCount();
        for(int i= 0;i<sections;i++){
            count+=1+getItemCountForSection(i)+(hasFooterInSection(i)? 1:0);
        }
        return count;
    }
    /**
     * 通过item的总数量，初始化几个数组:初始化与position相对应的section数组，
     * 初始化与section相对应的position的数组，初始化当前位置是否是一个Header的数组，
     * 初始化当前位置是否是一个Footer的数组
     *
     * @param count
     */
    private void setupArrays(int count){
        sectionForPosition= new int[count];
        positionWithinSection = new int [count];
        isHeader = new boolean[count];
        isFooter = new boolean[count];
    }

    private void calculatePositions(){
        int sections = getSectionCount();
        int index=0;

        for(int i=0;i<sections;i++){
            setupItems(index,true,false,i,0);
            index++;

            for(int j=0;j<getItemCountForSection(i);j++){
                setupItems(index,false,false,i,j);
                index++;
            }

            if(hasFooterInSection(i)){
                setupItems(index,false,true,i,0);
                index++;
            }
        }
    }
    /**
     * 保存每个位置对应的数据信息
     *
     * @param index    从0开始的每个最小单位所在的位置，从0开始，到count结束
     * @param isHeader 所在index位置的item是否是header
     * @param isFooter 所在index位置的item是否是footer
     * @param section  所在index位置的item对应的section
     * @param position 所在index位置的item对应的position
     */
    private void setupItems(int index,boolean isHeader,boolean isFooter,int section,int position){
        this.isHeader[index]=isHeader;
        this.isFooter[index]=isFooter;
        sectionForPosition[index] = section;
        positionWithinSection[index]=position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        RecyclerView.ViewHolder viewHolder;
        if(viewType ==TYPE_EMPTY){
            viewHolder = new EmptyViewHolder(emptyView);
        }else{
            switch (viewType){
                case TYPE_SECTION_HEADER:
                    viewHolder=onCreateSectionHeaderViewHolder(parent);
                    break;
                case TYPE_SECTION_FOOTER:
                    viewHolder=onCreateSectionFooterViewHolder(parent);
                    break;
                case TYPE_FOOTER:
                    viewHolder=onCreateFooterViewHolder(parent);
                    break;
                case TYPE_HEADER:
                    viewHolder= onCreateHeaderViewHolder(parent);
                    break;
                default:
                        viewHolder=onCreateItemViewHolder(parent);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(emptyViewVisible){

        }else{
            setViewHolder(holder,position);
        }
    }

    private void setViewHolder(RecyclerView.ViewHolder holder,final int position){
        if(hasHeader()){
            if(position ==0){
                onBindHeaderViewHolder((RH)holder);
            }else if(position<getItemCount()-1){
                final int section = sectionForPosition[position-1];
                int index = positionWithinSection[position-1];
                if(isSectionHeaderPosition(position-1)){
                    onBindSectionHeaderViewHolder((H)holder,section);
                    holder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            if(onSectionHeaderClickListener!=null){
                                onSectionHeaderClickListener.onSectionHeaderClick(section);
                            }
                        }
                    });
                }else if(isSectionFooterPosition(position-1)){
                    onBindSectionFooterViewHolder((F)holder,section);
                    holder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                                public void onClick(View v){
                            if(onSectionFooterClickListener!=null){
                                onSectionFooterClickListener.onSectionFooterClick(section);
                            }
                        }
                    });
                }else{
                    onBindItemViewHolder((VH)holder,section,index);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(onItemClickListener!=null){
                                onItemClickListener.onItemClick(section,position-1);
                            }
                        }
                    });

                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if(onItemLongClickListener !=null){
                                onItemLongClickListener.onItemLongClick(section,position-1);
                            }
                            return true;
                        }
                    });
                }
            }else{
                onBindFooterViewHolder((FO)holder);
            }
        }else{
            if(position<getItemCount()-1){
                final int section = sectionForPosition[position];
                int index=positionWithinSection[position];
                if(isSectionHeaderPosition(position)){
                    onBindSectionHeaderViewHolder((H)holder,section);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(onSectionHeaderClickListener!=null){
                                onSectionHeaderClickListener.onSectionHeaderClick(section);
                            }
                        }
                    });
                }else if(isSectionFooterPosition(position)){
                    onBindSectionFooterViewHolder((F)holder,section);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(onSectionFooterClickListener!=null){
                                onSectionFooterClickListener.onSectionFooterClick(section);
                            }
                        }
                    });
                }else {
                    onBindItemViewHolder((VH) holder, section, index);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(section, position);
                            }
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (onItemLongClickListener != null) {
                                onItemLongClickListener.onItemLongClick(section, position);
                            }
                            return true;
                        }
                    });
                }

            }else{
                onBindFooterViewHolder((FO)holder);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(sectionForPosition ==null){
            setupPosition();
        }
        if(emptyViewVisible){
            return TYPE_EMPTY;
        }else{
            if(hasHeader()){
                if(position==0){
                    return TYPE_HEADER;
                }else if(position <getItemCount()-1){
                    if(isSectionHeaderPosition(position-1)){
                        return TYPE_SECTION_HEADER;
                    }else if(isSectionFooterPosition(position-1)){
                        return TYPE_SECTION_FOOTER;
                    }else{
                        return TYPE_ITEM;
                    }
                }
                return TYPE_FOOTER;
            }else{
                if(position<getItemCount()-1){
                    if(isSectionHeaderPosition(position)){
                        return TYPE_SECTION_HEADER;
                    } else if (isSectionFooterPosition(position)) {
                        return TYPE_SECTION_FOOTER;
                    }else{
                        return TYPE_ITEM;
                    }
                }
                return TYPE_FOOTER;
            }
        }
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

    /**
     * 返回分组的数量
     *
     * @return
     */
    protected abstract int getSectionCount();

    /**
     * 返回当前分组的item数量
     *
     * @param section
     * @return
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * 当前分组是否有footer
     *
     * @param section
     * @return
     */
    protected abstract boolean hasFooterInSection(int section);

    /**
     * 整个列表是否有Header
     */
    protected abstract boolean hasHeader();


    /**
     * 对应位置是否是一个分组header
     */
    public boolean isSectionHeaderPosition(int position){
        if(isHeader ==null){
            setupPosition();
        }
        return isHeader[position];
    }

    public boolean isSectionFooterPosition(int position){
        if(isFooter==null){
            setupPosition();
        }
        return isFooter[position];
    }

    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent);

    /**
     * 为分组footer创建一个类型为F的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent);

    /**
     * 为分组内容创建一个类型为VH的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent);

    /**
     * 为整个列表创建一个类型为RH的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract RH onCreateHeaderViewHolder(ViewGroup parent);

    /**
     * 为整个列表创建一个类型为FO的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract FO onCreateFooterViewHolder(ViewGroup parent);

    /**
     * 绑定分组的Header数据
     *
     * @param holder
     * @param section
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * 绑定分组数据
     *
     * @param holder
     * @param section
     * @param position
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    /**
     * 绑定Header数据
     *
     * @param holder
     */
    protected abstract void onBindHeaderViewHolder(RH holder);

    /**
     * 绑定分组的footer数据
     *
     * @param holder
     * @param section
     */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);


    /**
     * 绑定上拉加载footer（整个RecycerView的footer）数据
     *
     * @param holder
     */
    protected void onBindFooterViewHolder(FO holder) {
        if(holder instanceof FooterHolder){
            FooterHolder footerHolder=(FooterHolder)holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footerHolder.tvFooter.setVisibility(View.VISIBLE);
                    footerHolder.tvFooter.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerHolder.tvFooter.setVisibility(View.VISIBLE);
                    footerHolder.tvFooter.setText("正在加载数据...");
                    break;
                case LOADING_FINISH:
                    footerHolder.tvFooter.setVisibility(View.VISIBLE);
                    footerHolder.tvFooter.setText("没有更多数据");
                    break;
                default:
                        footerHolder.tvFooter.setVisibility(View.GONE);
                        break;
            }
        }else {
            onBindFooterOtherviewHolder(holder);
        }
    }

    protected abstract void onBindFooterOtherviewHolder(FO holder);

}
