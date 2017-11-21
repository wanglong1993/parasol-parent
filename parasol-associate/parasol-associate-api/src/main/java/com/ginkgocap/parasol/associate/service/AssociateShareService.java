package com.ginkgocap.parasol.associate.service;

import com.ginkgocap.parasol.associate.model.AssociateShare;

/**
 * Created by xutlong on 2017/11/14.
 */
public interface AssociateShareService {

    /**
     * 存储关联设置的权限信息
     * @param associateShare
     * @return
     */
    public Long createAssociateShare(AssociateShare associateShare);

    /**
     * 获取关联设置的权限信息
     * @param id
     * @return
     */
    public String getAssociateShare(long id);
}
