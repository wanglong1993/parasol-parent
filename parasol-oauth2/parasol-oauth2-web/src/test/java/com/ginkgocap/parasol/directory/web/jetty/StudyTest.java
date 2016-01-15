package com.ginkgocap.parasol.directory.web.jetty;

import org.junit.Rule;
import org.junit.Test;

public class StudyTest {
	@Rule
	public RepeatableRule repeatableRule = new RepeatableRule(10,new String[]{"testHello"});
	
	
	@Test
	public void testHello() {
		System.out.println("ssssssss");
	}
	
}
