package org.parasol.column.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnSysService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnSysServiceTest {

	@Resource
	private ColumnSysService css;
	
	@Test
	public void testQuery(){
		ColumnSys cs=css.selectByPrimaryKey(1l);
		System.out.println(cs.getColumnname());
	}
}
