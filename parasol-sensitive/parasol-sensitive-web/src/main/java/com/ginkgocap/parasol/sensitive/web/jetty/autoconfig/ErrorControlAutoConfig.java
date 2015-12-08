package com.ginkgocap.parasol.sensitive.web.jetty.autoconfig;

import com.ginkgocap.parasol.sensitive.web.jetty.web.error.MessageErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public MessageErrorControl createMetadataErrorControl() {
		return new MessageErrorControl();
	}
}
