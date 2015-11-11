package com.ginkgocap.ywxt.metadata.service;

import java.util.List;

import com.ginkgocap.ywxt.metadata.model.CodeDy;


/**
 * 地域service接口
 * @author liu
 * @创建时间：2011-11-29 17:13:33
 */
public interface CodeDyService {
    @Deprecated
	CodeDy insert(CodeDy codeDy);

    @Deprecated
    void update(CodeDy codeDy);

    @Deprecated
    CodeDy selectByPrimarKey(long id);

    @Deprecated
    CodeDy selectByName(String name);

    /**
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/corporation/controller/CorporationFriendSearchController.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/corporation/controller/CorporationPersonalFriendSearchConroller.java:
     * @return
     */
    List<CodeDy> selectAll();

    @Deprecated
    void delete(long id);

}
