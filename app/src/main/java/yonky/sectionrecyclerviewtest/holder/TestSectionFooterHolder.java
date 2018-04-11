package yonky.sectionrecyclerviewtest.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView.ViewHolder;

import yonky.sectionrecyclerviewtest.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TestSectionFooterHolder extends ViewHolder {
    public TextView tvLookNum,tvLikeNum,tvEvaluateNum;
//    public LinearLayout llZan,llNum;
    public ImageView imgZan;

    public TestSectionFooterHolder(View itemView){
        super(itemView);
        initView();
    }
    private void initView(){
        tvLookNum = (TextView)itemView.findViewById(R.id.tv_look_num);
//        llNum=(LinearLayout)itemView.findViewById(R.id.ll_evaluate);
    }
}
