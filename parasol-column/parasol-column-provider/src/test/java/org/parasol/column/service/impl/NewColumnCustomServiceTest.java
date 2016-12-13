package org.parasol.column.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSysService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class NewColumnCustomServiceTest {
	
	@Resource
	private ColumnCustomService customService;
	
	@Resource
	private ColumnSysService sysService;
	
	@Test
	public void testQueryListByPidAndUserId(){
		Long uid=102542l;
		Long pcid=0l;
		List<ColumnSelf> list=customService.queryListByPidAndUserId(pcid, uid);
		System.out.println(list.size());
	}
	@Test
	public void testQueryDefaultList(){
		
		System.out.println("33");
		Long uid = 0l;
		Long pid = 0l;
		List<ColumnSelf> queryDefaultSysColumn = sysService.queryDefaultSysColumn(pid, uid);
		System.out.println("-------" + queryDefaultSysColumn.size());
				
	}
	@Test
	public void testReplace(){
		Long uid = 0l;
		Long pid = 0l;
		List<ColumnSelf> sysList = sysService.queryDefaultSysColumn(pid, uid);
		List<ColumnSelf> list = new ArrayList<ColumnSelf>();
		for (int i = 0; i < 15; i++) {
			ColumnSelf columnSelf = sysList.get(i);
			list.add(columnSelf);
		}
		System.out.println("****" +list.size());
		int i = customService.replace(110l, list);
		System.out.println("*********************"+ i + "*********************");
	}

}
