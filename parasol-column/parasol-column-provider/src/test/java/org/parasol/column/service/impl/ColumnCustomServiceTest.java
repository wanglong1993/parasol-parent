package org.parasol.column.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.service.ColumnCustomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnCustomServiceTest {
	
	@Resource
	private ColumnCustomService ccs;
	
	@Test
	public void testQueryListByPidAndUserId(){
		Long uid=0l;
		Long pcid=0l;
		List<ColumnCustom> list=ccs.queryListByPidAndUserId(pcid, uid);
		System.out.println(list.size());
	}

}
