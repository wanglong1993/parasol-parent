package com.ginkgocap.parasol.util;

import java.util.Comparator;

/**
 * Created by wf on 2017/5/3.
 * 按照 中文全拼 排序  （数字排在前面）
 * 例如： 1  2  3  a b c d 额 付 g h
 */
public class PinyinComparatorList implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        int i = concat((String) o1).compareTo(concat((String)o2));
        return i;
    }

    private String concat(String str) {

        String s = "";
        if (str != null) {
            s =  PinyinUtils.stringToQuanPin(str);
        }
        return s;
    }
}
