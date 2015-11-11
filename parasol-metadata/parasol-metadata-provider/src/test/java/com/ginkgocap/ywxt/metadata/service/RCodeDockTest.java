package com.ginkgocap.ywxt.metadata.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.RCodeDock;
import com.ginkgocap.ywxt.metadata.service.code.RCodeDockService;

@Configuration
public class RCodeDockTest  extends TestBase{
	@Resource
	RCodeDockService rCodeDockService;
	 public void allTest(){
		 RCodeDock rCodeDock = new RCodeDock();
		 rCodeDock.setTzId(1);
		 rCodeDock.setTzName("测试");
		 rCodeDock.setRzId(1);
		 rCodeDockService.save(rCodeDock);
		 
		 
	 }
}
