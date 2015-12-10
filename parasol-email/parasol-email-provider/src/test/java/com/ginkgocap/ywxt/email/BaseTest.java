package com.ginkgocap.ywxt.email;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
//@TransactionConfiguration(transactionManager = "emailTransactionManager")
//@Transactional
public class BaseTest {
	
	@BeforeClass
	public static void beforeClass(){
	}
	
	@Before
	public void before(){
	}
	
	@AfterClass
	public static void afterClass(){
	}
	
	@After
	public void after(){
	}
	
	@Test
	public void test(){
	}
}
