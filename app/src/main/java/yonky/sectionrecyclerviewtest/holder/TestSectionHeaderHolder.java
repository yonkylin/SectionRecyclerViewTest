package yonky.sectionrecyclerviewtest.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import yonky.sectionrecyclerviewtest.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TestSectionHeaderHolder extends RecyclerView.ViewHolder {
    public TextView tvNike,tvEvaluate,tvDate;
    public ImageView imgHead;

    public TestSectionHeaderHolder(View itemView){
        super(itemView);
        initView();
    }
    private void initView(){
        tvNike=(TextView) itemView.findViewById(R.id.tv_nike_name);
        tvEvaluate=(TextView)itemView.findViewById(R.id.tv_evaluate);
        tvDate=(TextView)itemView.findViewById(R.id.tv_date);
        imgHead=(ImageView)itemView.findViewById(R.id.img_head);
    }
}
