package org.parasol.column.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.dao.ColumnSysDao;
import org.parasol.column.entity.ColumnSys;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnSysDaoTest {

	@Resource
	private ColumnSysDao csd;
	
	@Test
	public void testQuery(){
		ColumnSys cs=csd.selectByPrimaryKey(1l);
		System.out.println(cs.getColumnname());
	}


}
