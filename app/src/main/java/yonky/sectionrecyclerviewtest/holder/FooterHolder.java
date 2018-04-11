package yonky.sectionrecyclerviewtest.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import yonky.sectionrecyclerviewtest.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterHolder(View itemView){
        super(itemView);
        initView();
    }

    private void initView(){
        tvFooter=(TextView)itemView.findViewById(R.id.tv_footer);
    }
}
