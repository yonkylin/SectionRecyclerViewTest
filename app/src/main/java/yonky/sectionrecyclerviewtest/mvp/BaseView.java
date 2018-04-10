package yonky.sectionrecyclerviewtest.mvp;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);
}
