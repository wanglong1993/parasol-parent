package com.ginkgocap.ywxt.metadata.service;

import java.util.List;

import com.ginkgocap.ywxt.metadata.model.EmailSuffix;
/**
 * 常用邮箱后缀
 * <p>  </p>
 * @author liubang
 * @date 2015-1-14
 */
public interface EmailSuffixService {
	/**
     * 通过主键获得邮箱后缀
     * @param id
     * @return
     */
	@Deprecated
	EmailSuffix findOne(long id);
    /**
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/QRCodeController.java
     * 查询所有的邮箱后缀
     * @return
     */
	
    List<String> findAll();
    /**
     * 插入或者更新邮箱后缀
     * @param code
     * @return
     */
    @Deprecated
    EmailSuffix saveOrUpdate(EmailSuffix emailSuffix);
    /**
     * 删除邮箱后缀
     * @param id
     */
    @Deprecated
    void delete(long id);
}
