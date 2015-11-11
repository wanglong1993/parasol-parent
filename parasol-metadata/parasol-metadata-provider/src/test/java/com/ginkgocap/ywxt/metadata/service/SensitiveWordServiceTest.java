package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.SensitiveWord;

@Configuration
public class SensitiveWordServiceTest  extends TestBase{
	@Resource
	SensitiveWordService sensitiveWordService;
	private SensitiveWord init(String word,int level,int type){
		SensitiveWord sw = new SensitiveWord();
		sw.setWord(word);
		sw.setLevel(level);
		sw.setType(type);
		return sw;
	}
	@Test
	public void AllTest(){
		SensitiveWord sw1 = init("1111", 1111, 1111);
		sw1 = sensitiveWordService.saveOrUpdate(sw1);
		assertEquals(sw1.getId()>0,true);
		SensitiveWord sw2 = init("2222", 2222, 2222);
		sw2 = sensitiveWordService.saveOrUpdate(sw2);
		assertEquals(sw2.getId()>0,true);
		Map<String,Object> param = new HashMap<String, Object>();
		DataGridModel dgm =  new DataGridModel();
		dgm.setPage(1);
		dgm.setRows(10);
		param.put("type", "1111");
		Map<String, Object> list= sensitiveWordService.findByParams(param, dgm);
		assertEquals(((List<SensitiveWord>)list.get("rows")).size()==1,true);
		param.put("level", "1111");
		Map<String, Object> list2= sensitiveWordService.findByParams(param, dgm);
		assertEquals(((List<SensitiveWord>)list2.get("rows")).size()==1,true);
		param.put("work", "1");
		Map<String, Object> list3= sensitiveWordService.findByParams(param, dgm);
		assertEquals(((List<SensitiveWord>)list3.get("rows")).size()==1,true);
		List<Long> idList = new ArrayList<Long>();
		idList.add(sw1.getId());
		idList.add(sw2.getId());
		Long[] ids= (Long[]) idList.toArray(new Long[idList.size()]);
		int i = sensitiveWordService.deleteByIds(ids);
		assertEquals(i>0,true);
		SensitiveWord sw3 =sensitiveWordService.findOne(sw1.getId());
		assertNull(sw3);
	}
}
