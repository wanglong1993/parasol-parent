package com.ginkgocap.parasol.directory.web.jetty.modle;

import java.io.Serializable;

/**
 * Created by wf on 2017/4/6.
 */
public class Ids implements Serializable{

    private static final long serialVersionUID = 3360254587306141397L;

    private Long[] ids;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
