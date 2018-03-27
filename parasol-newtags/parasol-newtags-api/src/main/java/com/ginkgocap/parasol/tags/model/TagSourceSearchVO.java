package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2018/3/7.
 */
public class TagSourceSearchVO extends TagSearchVO implements Serializable {

    private long totalcount;
    List<SourceSearchVO> sourceList;

    public long getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(long totalcount) {
        this.totalcount = totalcount;
    }

    public List<SourceSearchVO> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SourceSearchVO> sourceList) {
        this.sourceList = sourceList;
    }
}
