package yonky.sectionrecyclerviewtest.util;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ListUtil {
    public static<P> boolean isEmpty(List<P> list){
        return list==null || list.isEmpty();
    }
}
