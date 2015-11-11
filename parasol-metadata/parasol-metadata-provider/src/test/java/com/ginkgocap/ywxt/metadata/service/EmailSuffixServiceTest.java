package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.EmailSuffix;

public class EmailSuffixServiceTest  extends TestBase{
	@Resource
	EmailSuffixService emailSuffixService;
	@Test
	public void allTest(){
		EmailSuffix suf = new EmailSuffix();
		suf.setStatus(true);
		suf.setSuffix("@126.com");
		suf = emailSuffixService.saveOrUpdate(suf);
		assertEquals(suf.getId()>0, true);
		suf.setSuffix("@163.com");
		emailSuffixService.saveOrUpdate(suf);
		EmailSuffix suf2 =emailSuffixService.findOne(suf.getId());
		assertEquals(suf.getSuffix(), suf2.getSuffix());
		List<String> list = emailSuffixService.findAll();
		assertEquals(list.size()>0, true);
	}
	
	@Test
	public void testFndlAll() {
		
		List<String> list = emailSuffixService.findAll();
		assertEquals(list.size()> 0, true);
		
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("listEmailSuffix", list);
        System.out.println(JSONObject.fromObject(map));
	}
}
