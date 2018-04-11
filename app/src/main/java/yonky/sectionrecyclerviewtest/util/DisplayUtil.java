package yonky.sectionrecyclerviewtest.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/4/11.
 */

public class DisplayUtil {


    public static int getScreenWidthPixels(Context mContext){
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeightPixels(Context mContext){
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context mContext,int dp){
        final float scale= mContext.getResources().getDisplayMetrics().density;
        return (int)(dp*scale+0.5f);  //加0.5能让float转为int 时四舍五入，，结果更加精确
    }
    public static int px2dp(Context mContext,int px){
        final float scale=mContext.getResources().getDisplayMetrics().density;
        return (int)(px/scale +0.5f);
    }
}
