package org.parasol.column.mapper.gen;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnSys;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnSysMapperTest {

	@Resource
	private ColumnSysMapper columnSysMapper;
	
	@Test
	public void testQuery(){
//		ColumnSysExample example=new ColumnSysExample();
//		ColumnSysExample.Criteria c=example.createCriteria();
//		c.andIdEqualTo(1l);
		ColumnSys cs=columnSysMapper.selectByPrimaryKey(1l);
		System.out.println(cs.getColumnname());
	}
}
