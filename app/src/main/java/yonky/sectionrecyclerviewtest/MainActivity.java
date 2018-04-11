package yonky.sectionrecyclerviewtest;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yonky.sectionrecyclerviewtest.adapter.SectionedRecyclerViewAdapter;
import yonky.sectionrecyclerviewtest.adapter.SectionedSpanSizeLookup;
import yonky.sectionrecyclerviewtest.adapter.TestAdapter;
import yonky.sectionrecyclerviewtest.bean.TestEntity;
import yonky.sectionrecyclerviewtest.listener.LoadMoreListener;
import yonky.sectionrecyclerviewtest.mvp.Module;
import yonky.sectionrecyclerviewtest.mvp.TestPresenter;
import yonky.sectionrecyclerviewtest.util.ListUtil;
import yonky.sectionrecyclerviewtest.widges.SectionedGridDivider;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener ,Module.View
{

    @BindView(R.id.swip_root)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv)
    RecyclerView rv;



    private TestAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private SectionedGridDivider mDivider;
    private List<TestEntity.BodyBean.EListBean> mDatas = new ArrayList<>();
    private boolean isPull = true;

    private LoadMoreListener mLoadMoreListener;

    private TestPresenter mTestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTestPresenter = new TestPresenter(this);
        initAdapter();

    }

    private void initAdapter(){
        mAdapter = new TestAdapter(mDatas,this);
        mGridLayoutManager = new GridLayoutManager(this,3);
        mGridLayoutManager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,mGridLayoutManager));
        rv.setLayoutManager(mGridLayoutManager);
        rv.setAdapter(mAdapter);
        mDivider = new SectionedGridDivider(this,50, Color.parseColor("#F5F5F5"));
        rv.addItemDecoration(mDivider);

        mLoadMoreListener=new LoadMoreListener(mGridLayoutManager) {
            @Override
            public void onLoadMore() {
                isPull = false;
                isLoading=true;
                mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        mTestPresenter.loadData(1);
                    }
                },1000);
            }
        };
        rv.setOnScrollListener(mLoadMoreListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTestPresenter.loadData(1);
            }
        },300);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTestPresenter.loadData(0);
            }
        },1000);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void updateList(int type, List<TestEntity.BodyBean.EListBean> datas) {
        mLoadMoreListener.isLoading=false;
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(isPull){
            if(!ListUtil.isEmpty(datas)){
                mAdapter.getDatas().clear();
                mAdapter.notifyDataSetChanged();
                mAdapter.addMoreData(datas);
                if(mLoadMoreListener.isFullAScreen(rv)){
                    mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
                }else{
                    mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
                }
                mAdapter.notifyDataSetChanged();
            }else{
                //显示空布局
            }
        }else{
            if(datas.size()>0){
                mAdapter.addMoreData(datas);
                mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else{
                mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
        }
    }
}
