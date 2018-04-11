package yonky.sectionrecyclerviewtest.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import yonky.sectionrecyclerviewtest.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TestSectionBodyHodler extends RecyclerView.ViewHolder {
    public LinearLayout llRoot;
    public ImageView imgEvaluate;
    public TestSectionBodyHodler(View itemView){
        super(itemView);
        initView();
    }
    private void initView(){
        llRoot=(LinearLayout)itemView.findViewById(R.id.ll_root);
        imgEvaluate=(ImageView)itemView.findViewById(R.id.img_evaluate);
    }
}
