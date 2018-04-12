package yonky.sectionrecyclerviewtest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/12.
 */

public class TimeUtil {
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss sss");
        return dateFormat.format(date);
    }
}
