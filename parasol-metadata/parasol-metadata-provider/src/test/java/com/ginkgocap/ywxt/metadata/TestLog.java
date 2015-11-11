package com.ginkgocap.ywxt.metadata;

import org.apache.log4j.Logger;

public abstract class TestLog {
	protected  Logger logger = Logger.getLogger(getClass());
	
	public  void loggerInfo() {
		logger.info("SSSSSSSSSSSSSSSSSSSSSSSSSSSS");
	}
}
