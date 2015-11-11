/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.ywxt.metadata.service.region;

import java.util.List;

import com.ginkgocap.ywxt.metadata.model.CodeRegion;

/**
 * @author douyou 地域
 * @version 创建时间：2011-11-18 下午3:54:41
 * 类说明
 */
public interface CodeRegionService {
	@Deprecated
    CodeRegion selectByPrimaryKey(long id);
	
    List<CodeRegion> selectByParentId(long id);
    @Deprecated
    CodeRegion selectParentByPrimaryKey(long id);
    
    //.//phoenix-person/phoenix-person-provider/src/main/java/com/ginkgocap/ywxt/person/service/impl/PersonServiceImpl.java
    //.//phoenix-web/src/main/java/com/ginkgocap/ywxt/common/Base.java
    //.//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/CodeRegionController.java
    List<CodeRegion> selectAllCountry();
    
    
    //.//jtmobileserver/src/main/java/com/ginkgocap/ywxt/metadata/controller/MetadataController.java
    
    
    List<CodeRegion> selectByName(String name);
}