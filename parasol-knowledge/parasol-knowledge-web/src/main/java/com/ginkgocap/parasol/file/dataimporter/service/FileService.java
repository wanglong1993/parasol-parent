/**
 * Copyright (c) 2014 北京金桐网投资有限公司.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.file.dataimporter.service;

import java.util.List;

import com.ginkgocap.parasol.file.model.Index;

/**
 * 
* @Title: FileService.java
* @Package com.ginkgocap.parasol.file.dataimporter.service
* @Description: TODO(用一句话描述该文件做什么)
* @author fuliwen@gintong.com  
* @date 2016年2月22日 下午4:22:34
* @version V1.0
 */
public interface FileService {

    /**
     * 通过主键获得文章记录
     * @param id
     * @return
     */
    List<Index> getAllFileIndexes(int start, int limit);
}
