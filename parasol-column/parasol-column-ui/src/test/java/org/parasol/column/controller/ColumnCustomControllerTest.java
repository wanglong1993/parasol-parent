package org.parasol.column.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.utils.HttpUtils;
import org.parasol.column.utils.JsonUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnCustomControllerTest {
	
	/*@Resource(name="columnCustomService")
	private ColumnCustomService ccs;*/
	
	@Resource(name="columnSelfService")
	private ColumnSelfService css;
	
	@Test
	public void testShow(){
//		ccs.queryListByPidAndUserId(0l, 0l);
	}
	
	@Test
	public void testAddColumn() throws Exception{
		ColumnSelf cs=css.selectByPrimaryKey(1l);
		cs.setColumnname("user1");
		cs.setTags("abc,cde");
		String jsonStr=JsonUtils.beanToJson(cs);
		String url="http://localhost:8090/parasol-column-ui/columnself/addColumnSelf";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}

}
