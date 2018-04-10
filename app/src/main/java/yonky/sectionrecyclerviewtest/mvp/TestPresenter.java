package yonky.sectionrecyclerviewtest.mvp;

import java.util.List;

import yonky.sectionrecyclerviewtest.bean.TestEntity;
import yonky.sectionrecyclerviewtest.util.DatasUtil;

/**
 * Created by Administrator on 2018/4/10.
 */

public class TestPresenter implements Module.Presenter {
    private Module.View view;
    public TestPresenter(Module.View view){
        this.view = view;
    }

    @Override
    public void loadData(int loadType) {
        List<TestEntity.BodyBean.EListBean> datas = DatasUtil.createDatas();
        if(view!= null){
            view.updateList(loadType,datas);
        }
    }
}
