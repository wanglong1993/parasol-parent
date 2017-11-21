package com.ginkgocap.parasol.util;

import com.ginkgocap.parasol.directory.model.Directory;

import java.util.Comparator;

/**
 * Created by wf on 2017/5/3.
 * 以后写 含有 name 属性的公共方法
 */
public class PinyinComparatorList4ObjectName implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {

        return concat(((Directory) o1).getName()).compareTo(concat(((Directory) o2).getName()));
    }

    private String concat(String str) {

        String s = "";
        if (str != null) {
            s = PinyinUtils.stringToQuanPin(str);
        }
        return s;
    }
}
