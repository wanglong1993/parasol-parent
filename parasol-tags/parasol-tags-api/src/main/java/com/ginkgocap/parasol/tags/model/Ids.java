package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang fei on 2017/8/14.
 */
public class Ids<T> implements Serializable {

    private static final long serialVersionUID = 2211712290836358494L;

    private long id;

    private List<T> idList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<T> getIdList() {
        return idList;
    }

    public void setIdList(List<T> idList) {
        this.idList = idList;
    }
}
