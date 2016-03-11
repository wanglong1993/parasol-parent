package com.ginkgocap.parasol.knowledge.web.jetty.autoconfig;

import com.ginkgocap.parasol.knowledge.web.jetty.web.error.SensitiveErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public SensitiveErrorControl createMetadataErrorControl() {
		return new SensitiveErrorControl();
	}
}
