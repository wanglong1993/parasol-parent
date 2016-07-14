package com.ginkgocap.parasol.organ.web.jetty.web.service;

import com.ginkgocap.ywxt.metadata.model.UserTag;
/**
 * 组织标签保存
 * @author hdy
 * @date 2015-4-10
 */
public interface OrganTagService {
     public UserTag save(String tagName,long userId);
}

