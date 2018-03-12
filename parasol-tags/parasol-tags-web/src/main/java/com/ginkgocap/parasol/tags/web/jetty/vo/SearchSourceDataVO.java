package com.ginkgocap.parasol.tags.web.jetty.vo;

import com.ginkgocap.parasol.tags.model.SourceSearchVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2018/3/7.
 */
public class SearchSourceDataVO implements Serializable{

    private List<SourceSearchVO> list;

    private long count;

    public SearchSourceDataVO() {
    }

    public SearchSourceDataVO(List<SourceSearchVO> list, long count) {
        this.list = list;
        this.count = count;
    }

    public List<SourceSearchVO> getList() {
        return list;
    }

    public void setList(List<SourceSearchVO> list) {
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
