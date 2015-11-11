/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.CodeRegion;
import com.ginkgocap.ywxt.metadata.service.region.CodeRegionService;

/**
 * @author douyou
 * 
 * @创建时间：2010-8-4
 * 
 * @功能说明：UserDao测试用例
 */
@Configuration
public class CodeRegionServiceTest extends TestBase{

    @Autowired
    private CodeRegionService codeRegionDAO;

    @Test
    public void testSelect() {
        List lt= codeRegionDAO.selectByParentId(0);
        assertNotNull(lt);
        CodeRegion code = codeRegionDAO.selectByPrimaryKey(1);
        assertNotNull(code);
        code = codeRegionDAO.selectParentByPrimaryKey(1);
        assertNull(code);
    }
    
    @Test
    public void testSelectByName() {
    	List<CodeRegion> codeRegions = codeRegionDAO.selectByName("美");
    	assertNotNull(codeRegions);
    	for (CodeRegion codeRegion : codeRegions) {
			System.out.println(codeRegion.getCname());
		}
    }
}
