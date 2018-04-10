package yonky.sectionrecyclerviewtest.mvp;

import java.util.List;

import yonky.sectionrecyclerviewtest.bean.TestEntity;

/**
 * Created by Administrator on 2018/4/10.
 */

public class Module {
    public interface View extends BaseView{
        void updateList(int type,List<TestEntity.BodyBean.EListBean> datas);
    }

    public interface Presenter extends BasePresenter{
        void loadData(int loadType);
    }
}
