package yonky.sectionrecyclerviewtest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yonky.sectionrecyclerviewtest.bean.TestEntity;
import yonky.sectionrecyclerviewtest.mvp.Module;
import yonky.sectionrecyclerviewtest.mvp.TestPresenter;

public class MainActivity extends AppCompatActivity
//        implements SwipeRefreshLayout.OnRefreshListener ,Module.View
{

    @BindView(R.id.swip_root)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv)
    RecyclerView rv;

//    private TestAdapter mAdapter;
//    private GridLayoutManager mGridLayoutManager;
//    private SectionedGridDivider mDivider;
//    private List<TestEntity.BodyBean.EListBean> mDatas = new ArrayList<>();
//    private boolean isPull = true;
//
//    private LoadMoreListener mLoadMoreListener;
//
//    private TestPresenter mTestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTestPresenter = new TestPresenter(this);
//        initAdapter();
    }
//
//    @Override
//    public void updateList(int type, List<TestEntity.BodyBean.EListBean> datas) {
//
//    }
}
