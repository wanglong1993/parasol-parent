package org.parasol.column.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.service.ColumnCustomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)
public class ColumnCustomControllerTest {
	
	/*@Resource(name="columnCustomService")
	private ColumnCustomService ccs;*/
	
	@Test
	public void testShow(){
//		ccs.queryListByPidAndUserId(0l, 0l);
	}

}
