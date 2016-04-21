package org.parasol.column.mapper.gen;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSelfExample;
import org.parasol.column.entity.ColumnSelfExample.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnSelfMapperTest {
	
	@Resource
	private ColumnSelfMapper csm;
	
	@Test
	public void testLimit(){
		Long uid=0l;
		Long pid=0l;
		ColumnSelfExample example=new ColumnSelfExample();
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(pid);
		c.andUserIdEqualTo(uid);
		example.setOrderByClause("order_num desc");
		example.setLimit(1);
		List<ColumnSelf> list=csm.selectByExampleLimit(example);
		System.out.println(list.size());
	}

}
