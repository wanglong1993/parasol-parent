package com.ginkgocap.parasol.file.web.jetty.autoconfig;

import com.ginkgocap.parasol.file.web.jetty.web.error.SensitiveErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public SensitiveErrorControl createMetadataErrorControl() {
		return new SensitiveErrorControl();
	}
}
